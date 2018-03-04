package expression.parser;

import expression.*;
import expression.exceptions.*;

public class ExpressionParser implements Parser {

    private String str;
    private int index;
    private boolean negate;
    private int balance;
    private String curToken;
    private String prevToken;
    private int prevIndex;

    public TripleExpression parse(String expression) throws ParseException {
        index = 0;
        str = expression;
        negate = false;
        balance = 0;
        getNextAndCheck();
        TripleExpression result = addAndSubtract();
        if (index < str.length()) {
            String name = getToken();
            String type = " ";
            if (!(name.equals("(") || name.equals(")"))) {
                type = " operation ";
            }
            throw new ParseException("unexpected" + type + "\"" + name + "\" at index " + (index + 1));
        }
        return result;
    }

    private boolean isVariable(String s) {
        return s.equals("x") || s.equals("y") || s.equals("z");
    }

    private String getToken() {
        if (Character.isLetter(str.charAt(index))) {
            int i = index;
            while (i < str.length() && (Character.isLetter(str.charAt(i)) || Character.isDigit(str.charAt(i)))) {
                i++;
            }
            return str.substring(index, i);
        }
        return Character.toString(str.charAt(index));
    }

    private void getNextAndCheck() throws ParseException {
        while (index < str.length() && Character.isWhitespace(str.charAt(index))) {
            index++;
        }
        if (index < str.length()) {
            String check = getToken();
            String type = "";
            if (Character.isLetter(check.charAt(0))) {
                if (!(check.equals("abs") || check.equals("sqrt") || isVariable(check))) {
                    type = "operation";
                }
            } else {
                char c = check.charAt(0);
                if (!(Character.isDigit(c) || c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')')) {
                    type = "symbol";
                }
            }
            if (!type.equals("")) {
                throw new ParseException("unknown " + type + " at index " + (index + 1) + ": \"" + check + "\"");
            }
            curToken = check;
        } else {
            curToken = "\0";
        }
    }

    private boolean compare(String operationOrSymbol) throws ParseException {
        if (curToken.equals(operationOrSymbol)) {
            prevToken = operationOrSymbol;
            prevIndex = index;
            index += operationOrSymbol.length();
            getNextAndCheck();
            return true;
        }
        return false;
    }

    private TripleExpression addAndSubtract() throws ParseException {
        TripleExpression result = multiplyAndDivide();
        while (true) {
            if (compare("+")) {
                result = new CheckedAdd(result, multiplyAndDivide());
            } else if (compare("-")) {
                result = new CheckedSubtract(result, multiplyAndDivide());
            } else {
                return result;
            }
        }
    }

    private TripleExpression multiplyAndDivide() throws ParseException {
        TripleExpression result = unaryOperations();
        while (index < str.length()) {
            if (compare("*")) {
                result = new CheckedMultiply(result, unaryOperations());
            } else if (compare("/")) {
                result = new CheckedDivide(result, unaryOperations());
            } else {
                break;
            }
        }
        return result;
    }

    private TripleExpression unaryOperations() throws ParseException {
        if (compare("-")) {
            if (Character.isDigit(str.charAt(index))) {
                return number(true);
            }
            return new CheckedNegate(unaryOperations());
        }
        if (compare("abs")) {
            return new CheckedAbs(unaryOperations());
        }
        if (compare("sqrt")) {
            return new CheckedSqrt(unaryOperations());
        }
        return brackets();
    }

    private TripleExpression brackets() throws ParseException {
        if (compare("(")) {
            balance++;
            TripleExpression result = addAndSubtract();
            if (curToken.equals("\0") || str.charAt(index) != ')') {
                throw new ParseException("expected " + balance + " more \")\" in expression");
            }
            index++;
            balance--;
            getNextAndCheck();
            return result;
        } else if (index < str.length() && isVariable(Character.toString(str.charAt(index)))) {
            TripleExpression result = new Variable(Character.toString(str.charAt(index)));
            index++;
            getNextAndCheck();
            return result;
        } else {
            return number(false);
        }
    }

    private TripleExpression number(boolean negate) throws ParseException{
        if (curToken.equals("\0") || !Character.isDigit(str.charAt(index))) {
            String name = prevToken;
            if (name == null) {
                name = getToken();
            }
            throw new ParseException("no argument for \"" + name + "\" at index " + (prevIndex + 1));
        }
        int startIndex = index;
        while (index < str.length() && Character.isDigit(str.charAt(index))) {
            index++;
        }
        String cnstStr = str.substring(startIndex, index);
        if (negate) {
            cnstStr = "-" + cnstStr;
            startIndex--;
        }
        int value;
        try {
            value = Integer.parseInt(cnstStr);
        } catch (NumberFormatException e) {
            throw new ParseException("constant overflow at index " + (startIndex + 1) + ": " + cnstStr);
        }
        getNextAndCheck();
        return new Const(value);
    }
}
