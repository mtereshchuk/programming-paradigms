package expression.generic;

import expression.exception.*;

public class OperationInteger implements Operation<Integer> {
    public Integer add(Integer first, Integer second) throws OverflowException {
        if (first > 0) {
            if (second > Integer.MAX_VALUE - first) {
                throw new OverflowException();
            }
        } else if (second < Integer.MIN_VALUE - first) {
            throw new OverflowException();
        }
        return first + second;
    }

    public Integer subtract(Integer first, Integer second) throws OverflowException {
        if (second < 0) {
            if (first > Integer.MAX_VALUE + second) {
                throw new OverflowException();
            }
        } else if (first < Integer.MIN_VALUE + second) {
            throw new OverflowException();
        }
        return first - second;
    }

    public Integer multiply(Integer first, Integer second) throws OverflowException {
        if (first > 0) {
            if (second > Integer.MAX_VALUE / first || second < Integer.MIN_VALUE / first) {
                throw new OverflowException();
            }
        } else if (first < -1) {
            if (second > Integer.MIN_VALUE / first || second < Integer.MAX_VALUE / first) {
                throw new OverflowException();
            }
        } else if (first == -1 && second == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return first * second;
    }

    public Integer divide(Integer first, Integer second) throws DivisionByZeroException, OverflowException {
        if (second == 0) {
            throw new DivisionByZeroException();
        }
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new OverflowException();
        }
        return first / second;
    }

    public Integer negate(Integer first) throws OverflowException {
        if (first == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return -first;
    }

    public Integer parse(String s) throws OverflowException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new OverflowException();
        }
    }

    public Integer converse(int first) { return first; }

    public Integer abs(Integer first) throws OverflowException {
        if (first == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        if (first >= 0) {
            return first;
        }
        return -first;
    }

    public Integer square(Integer first) throws OverflowException {
        return multiply(first, first);
    }

    public Integer mod(Integer first, Integer second) {
        return first % second;
    }
}
