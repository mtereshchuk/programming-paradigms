package expression.exception;

public class DivisionByZeroException extends Exception {
    public DivisionByZeroException() {
        super("division by zero");
    }
}
