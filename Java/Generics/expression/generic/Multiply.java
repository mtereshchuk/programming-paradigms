package expression.generic;

import expression.TripleExpression;

public class Multiply<T> extends BinaryOperation<T> {

    public Multiply(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    protected T evaluateImpl(T first, T second) throws Exception {
        return operation.multiply(first, second);
    }
}