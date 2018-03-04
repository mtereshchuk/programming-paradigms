package expression.generic;

import expression.TripleExpression;

abstract public class BinaryOperation<T> implements TripleExpression<T> {

    private TripleExpression<T> first;
    private TripleExpression<T> second;
    protected Operation<T> operation;

    BinaryOperation(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    public T evaluate(T x, T y, T z) throws Exception {
        return evaluateImpl(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    abstract protected T evaluateImpl(T first, T second) throws Exception;
}
