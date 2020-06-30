package expression.exceptions;

public class UnexpectedSymbolException extends ParsingException{
    public UnexpectedSymbolException(final char chr, final String expr, final int index) {
        super("Unexpected symbol " + chr + "\n" + expr + positionMark(index));
    }
}
