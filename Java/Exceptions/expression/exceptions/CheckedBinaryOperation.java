package expression.exceptions;

import expression.*;

public abstract class CheckedBinaryOperation implements TripleExpression {

    private TripleExpression first, second;

    protected CheckedBinaryOperation(TripleExpression first, TripleExpression second) {
        this.first = first;
        this.second = second;
    }

    public int evaluate(int x, int y, int z) throws CalculateException {
        return evaluateImpl(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    protected abstract int evaluateImpl(int first, int second) throws CalculateException;
}