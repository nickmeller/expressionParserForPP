package expression.exceptions;

public class MissingBracketException extends ParsingException {
    public MissingBracketException(final String expr, final int index) {
        super("Bracket expected: \n" + expr + "\n" + positionMark(index));
    }
}
