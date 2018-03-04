package expression.exceptions;

import expression.*;

public class CheckedAdd extends CheckedBinaryOperation {

    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int evaluateImpl(int first, int second) throws CalculateException {
        if (first > 0) {
            if (second > Integer.MAX_VALUE - first) {
                throw new CalculateException("overflow");
            }
        } else if (second < Integer.MIN_VALUE - first) {
            throw new CalculateException("overflow");
        }
        return first + second;
    }
}