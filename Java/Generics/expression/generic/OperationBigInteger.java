package expression.generic;

import java.math.BigInteger;

public class OperationBigInteger implements Operation<BigInteger> {

    public BigInteger add(BigInteger first, BigInteger second) {
        return first.add(second);
    }

    public BigInteger subtract(BigInteger first, BigInteger second) {
        return first.subtract(second);
    }

    public BigInteger multiply(BigInteger first, BigInteger second) {
        return first.multiply(second);
    }

    public BigInteger divide(BigInteger first, BigInteger second) {
        return first.divide(second);
    }

    public BigInteger negate(BigInteger first) {
        return first.negate();
    }

    public BigInteger parse(String s) {
        return new BigInteger(s);
    }

    public BigInteger converse(int first) { return BigInteger.valueOf(first); }

    public BigInteger abs(BigInteger first) {
        return first.abs();
    }

    public BigInteger square(BigInteger first) {
        return multiply(first, first);
    }

    public BigInteger mod(BigInteger first, BigInteger second) {
        return first.remainder(second);
    }
}