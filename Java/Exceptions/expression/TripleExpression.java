package expression;

import expression.exceptions.CalculateException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression {
    int evaluate(int x, int y, int z) throws CalculateException;
}
