package expression.generic;

import expression.TripleExpression;

public class Subtract<T> extends BinaryOperation<T>  {

    public Subtract(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    protected T evaluateImpl(T first, T second) throws Exception {
        return operation.subtract(first, second);
    }
}