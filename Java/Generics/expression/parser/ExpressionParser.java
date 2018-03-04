package expression.parser;

import expression.TripleExpression;
import expression.generic.*;

public class ExpressionParser<T> implements Parser<T> {

    private String str;
    private int index;
    private Operation<T> operation;

    public ExpressionParser(Operation<T> operation) {
        this.operation = operation;
    }

    private String checkAndClean(String expression) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char currentSymbol = expression.charAt(i);
            if (!Character.isWhitespace(currentSymbol)) {
                result.append(currentSymbol);
            }
        }
        return result.toString();
    }

    public TripleExpression<T> parse(String expression) throws Exception {
        index = 0;
        str = checkAndClean(expression);
        return addAndSubtract();
    }

    private boolean compare(char symbol) {
        if (str.charAt(index) == symbol) {
            index++;
            return true;
        }
        return false;
    }

    private TripleExpression<T> addAndSubtract() throws Exception {
        TripleExpression<T> result = multiplyAndDivideAndMod();
        while (index < str.length()) {
            if (compare('+')) {
                result = new Add<>(result, multiplyAndDivideAndMod(), operation);
            } else if (compare('-')) {
                result = new Subtract<>(result, multiplyAndDivideAndMod(), operation);
            } else {
                break;
            }
        }
        return result;
    }

    private TripleExpression<T> multiplyAndDivideAndMod() throws Exception {
        TripleExpression<T> result = unaryMinusAndAbsAndSquare();
        while (index < str.length()) {
            if (compare('*')) {
                result = new Multiply<>(result, unaryMinusAndAbsAndSquare(), operation);
            } else if (compare('/')) {
                result = new Divide<>(result, unaryMinusAndAbsAndSquare(), operation);
            } else if (compare('m')) {
                index+= 2;
                result = new Mod<>(result, unaryMinusAndAbsAndSquare(), operation);
            } else {
                break;
            }
        }
        return result;
    }

    private TripleExpression<T> unaryMinusAndAbsAndSquare() throws Exception{
        if (compare('-')) {
            if (Character.isDigit(str.charAt(index))) {
                return numberAndVariable(true);
            } else {
                return new Negate<>(unaryMinusAndAbsAndSquare(), operation);
            }
        }
        if (compare('a')) {
            index += 2;
            return new Abs<>(unaryMinusAndAbsAndSquare(), operation);
        }
        if (compare('s')) {
            index += 5;
            return new Square<>(unaryMinusAndAbsAndSquare(), operation);
        }
        return brackets();
    }

    private TripleExpression<T> brackets() throws Exception {
        if (compare('(')) {
            TripleExpression<T> result = addAndSubtract();
            index++;
            return result;
        }
        return numberAndVariable(false);
    }

    private TripleExpression<T> numberAndVariable(boolean negate) throws Exception {
        if (Character.isDigit(str.charAt(index))) {
            int startIndex = index;
            while (index < str.length() && (Character.isDigit(str.charAt(index)) || str.charAt(index) == '.')) {
                index++;
            }
            String s = str.substring(startIndex, index);
            if (negate) {
                s = "-" + s;
            }
            T constant;
            constant = operation.parse(s);
            return new Const<>(constant);
        }
        TripleExpression<T> result = new Variable<>(Character.toString(str.charAt(index)));
        index++;
        return result;
    }
}
