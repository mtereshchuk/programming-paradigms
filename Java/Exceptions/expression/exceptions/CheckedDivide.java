package expression.exceptions;

import expression.*;

public class CheckedDivide extends CheckedBinaryOperation {

    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int evaluateImpl(int first, int second) throws CalculateException {
        if (second == 0) {
            throw new CalculateException("division by zero");
        }
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new CalculateException("overflow");
        }
        return first / second;
    }
}
