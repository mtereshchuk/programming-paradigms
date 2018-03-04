package expression.exceptions;

import expression.*;

public class CheckedSubtract extends CheckedBinaryOperation {

    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int evaluateImpl(int first, int second) throws CalculateException {
        if (second < 0) {
            if (first > Integer.MAX_VALUE + second) {
                throw new CalculateException("overflow");
            }
        } else if (first < Integer.MIN_VALUE + second) {
            throw new CalculateException("overflow");
        }
        return first - second;
    }
}