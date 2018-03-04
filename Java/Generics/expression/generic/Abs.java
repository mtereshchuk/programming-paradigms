package expression.generic;

import expression.TripleExpression;

public class Abs<T> implements TripleExpression<T> {

    private TripleExpression<T> first;
    private Operation<T> operation;

    public Abs(TripleExpression<T> first, Operation<T> operation) {
        this.first = first;
        this.operation = operation;
    }

    public T evaluate(T x, T y, T z) throws Exception {
        T value = first.evaluate(x, y, z);
        return operation.abs(value);
    }
}