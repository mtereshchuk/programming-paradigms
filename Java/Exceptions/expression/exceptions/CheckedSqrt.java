package expression.exceptions;

import expression.*;

public class CheckedSqrt implements TripleExpression {

    private TripleExpression first;

    public CheckedSqrt(TripleExpression first) {
        this.first = first;
    }

    public int evaluate(int x, int y, int z) throws CalculateException {
        int value = first.evaluate(x, y, z);
        if (value < 0) {
            throw new CalculateException("sqrt from negative argument");
        }
        int l = -1;
        int r = 46341;
        while (l < r - 1) {
            int m = (l + r) / 2;
            if (m * m <= value) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }
}
