package expression.generic;

import expression.TripleExpression;
import expression.parser.ExpressionParser;

public class GenericTabulator implements Tabulator {

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return fillTable(getOperation(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private Operation<?> getOperation(String mode) throws Exception {
        switch (mode) {
            case "i":
                return new OperationInteger();
            case "d":
                return new OperationDouble();
            case "bi":
                return new OperationBigInteger();
            default:
                throw new Exception("Incorrect mode");
        }
    }

    private <T> Object[][][] fillTable (Operation<T> operation, String expression,
                                       int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 -z1 + 1];
        TripleExpression<T> parsedExpression = new ExpressionParser<>(operation).parse(expression);
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        table[i - x1][j - y1][k - z1] = parsedExpression.evaluate(operation.converse(i),
                                operation.converse(j), operation.converse(k));
                    } catch (Throwable e) {
                        table[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return table;
    }
}
