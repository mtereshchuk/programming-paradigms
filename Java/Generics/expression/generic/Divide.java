package expression.generic;

import expression.TripleExpression;

public class Divide<T> extends BinaryOperation<T>  {

    public Divide(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    protected T evaluateImpl(T first, T second) throws Exception {
        return operation.divide(first, second);
    }
}