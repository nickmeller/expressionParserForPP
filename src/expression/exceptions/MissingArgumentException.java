package expression.exceptions;

public class MissingArgumentException extends ParsingException{
    public MissingArgumentException(final String expr, final int index) {
        super("Argument expected: " + "\n" + expr + "\n" + positionMark(index));
    }
}
