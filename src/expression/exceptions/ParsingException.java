package expression.exceptions;

public class ParsingException extends Exception{

    public ParsingException(final String message) {
        super(message);
    }

    static protected String positionMark(final int index) {
        String marker = " ".repeat(Math.max(0, index)) +
                '^';
        return marker;
    }
}
