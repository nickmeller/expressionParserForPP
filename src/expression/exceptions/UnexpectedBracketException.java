package expression.exceptions;

public class UnexpectedBracketException extends ParsingException {
    public UnexpectedBracketException(final String str, final int index) {
        super("Unexpected closing bracket at position " + index + "\n" + str + "\n" + positionMark(index));
    }
}
