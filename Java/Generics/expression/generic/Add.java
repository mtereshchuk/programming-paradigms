package expression.generic;

import expression.TripleExpression;

public class Add<T> extends BinaryOperation<T> {

    public Add(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    protected T evaluateImpl(T first, T second) throws Exception {
        return operation.add(first, second);
    }
}