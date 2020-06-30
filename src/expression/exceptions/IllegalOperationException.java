package expression.exceptions;

public class IllegalOperationException extends EvaluatingException {
    public IllegalOperationException(String message) {
        super("Illegal operation: " + message);
    }
}
