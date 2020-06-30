package expression.exceptions;

public class MissingOperatorException extends ParsingException{
    public MissingOperatorException(final String expr, final int index) {
        super("Operator expected: \n" + expr + "\n" + positionMark(index));
    }
}
