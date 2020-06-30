package expression.exceptions;

public class UnresolvedTokenException extends ParsingException{

    public UnresolvedTokenException(final String seq, final String expr, final int index) {
        super("Unexpected sequence: " + seq + "\n" + expr + positionMark(index));
    }
}
