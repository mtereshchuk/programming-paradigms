package expression.exceptions;

import expression.*;

public class CheckedMultiply extends CheckedBinaryOperation {

    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int evaluateImpl(int first, int second) throws CalculateException {
        if (first > 0) {
            if (second > Integer.MAX_VALUE / first || second < Integer.MIN_VALUE / first) {
                throw new CalculateException("overflow");
            }
        } else if (first < -1) {
            if (second > Integer.MIN_VALUE / first || second < Integer.MAX_VALUE / first) {
                throw new CalculateException("overflow");
            }
        } else if (first == -1 && second == Integer.MIN_VALUE) {
            throw new CalculateException("overflow");
        }
        return first * second;
    }
}