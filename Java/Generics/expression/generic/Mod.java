package expression.generic;

import expression.TripleExpression;

public class Mod<T> extends BinaryOperation<T> {

    public Mod(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    protected T evaluateImpl(T first, T second) throws Exception {
        return operation.mod(first, second);
    }
}