package expression.exceptions;

import expression.*;

public class CheckedAbs implements TripleExpression {

    private TripleExpression first;

    public CheckedAbs(TripleExpression first) {
        this.first = first;
    }

    public int evaluate(int x, int y, int z) throws CalculateException {
        int value = first.evaluate(x, y, z);
        if (value == Integer.MIN_VALUE) {
            throw new CalculateException("overflow");
        }
        if (value >= 0) {
            return value;
        }
        return -value;
    }
}
