package expression.parser;

import expression.*;
import expression.exceptions.*;

import java.util.EnumSet;
import java.util.Set;

public class ExpressionParser implements Parser {

    private String expression;
    private int index;
    private int balance;

    private enum Token {
        ADD, BEGIN, DIV, END, OPEN_BRACE,MUL, NEGATE, NUMBER, CLOSE_BRACE, SUB, VARIABLE,
        SHL, SHR;
    }

    private Token currToken;
    private int number;
    private String variableName;

    @Override
    public TripleExpression parse(String expression) throws ParsingException, OverflowException {
        index = 0;
        balance = 0;
        this.expression = expression;
        currToken = Token.BEGIN;
        return getAddAndSubtract();
    }

    private void skipWS() {
        while(index < expression.length() && Character.isWhitespace(expression.charAt(index))) {index++;}
    }

    private void nextToken() throws ParsingException, OverflowException {
        skipWS();
        if (index >= expression.length()) {
            currToken = Token.END;
            return;
        }

        char temp = expression.charAt(index);

        switch(temp) {
            case '+':
                checkOperandFlow();
                currToken = Token.ADD;
                break;
            case '-':
                if (currToken == Token.NUMBER || currToken == Token.VARIABLE || currToken == Token.CLOSE_BRACE) {
                    checkOperandFlow();
                    currToken = Token.SUB;
                } else {
                    if (index + 1 >= expression.length()) {
                        throw new MissingArgumentException(expression, index);
                    }
                    if (Character.isDigit(expression.charAt(index + 1))) {
                        index++;
                        String num = "-" + nextNumberString();
                        try {
                            number = Integer.parseInt(num);
                        } catch (NumberFormatException e) {
                            throw new OverflowException();
                        }
                        currToken = Token.NUMBER;
                    } else {
                        currToken = Token.NEGATE;
                    }
                }
                break;
            case '*':
                checkOperandFlow();
                currToken = Token.MUL;
                break;
            case '/':
                checkOperandFlow();
                currToken = Token.DIV;
                break;
            case '(':
                checkOperatorFlow();
                currToken = Token.OPEN_BRACE;
                balance++;
                break;
            case ')':
                balance--;
                currToken = Token.CLOSE_BRACE;
                if (balance < 0) {
                    throw new UnexpectedBracketException(expression, index);
                }
                break;
            default:
                if (Character.isDigit(temp)) {
                    checkOperatorFlow();
                    String num = nextNumberString();
                    try {
                        number = Integer.parseInt(num);
                    } catch (NumberFormatException e) {
                        throw new OverflowException();
                    }
                    currToken = Token.NUMBER;
                } else if (temp == 'x' || temp == 'y' || temp == 'z') {
                    currToken = Token.VARIABLE;
                    variableName = Character.toString(temp);
                } else {
                    if (index == expression.length() - 1) {
                        throw new UnexpectedSymbolException(expression.charAt(index), expression, index);
                    }
                    char nextChar = expression.charAt(++index);

                    if (nextChar == ' ' || index == expression.length() - 1) {
                        throw new UnexpectedSymbolException(expression.charAt(index), expression, index);
                    } else {
                        int left = index - 1;
                        int right = index;
                        while (index < expression.length() - 1 && nextChar != ' ') {
                            right++;
                            nextChar = expression.charAt(++index);
                        }
                        throw new UnresolvedTokenException(expression.substring(left,
                                right), expression, index);
                    }
                }
        }

        index++;
    }


    private final Set<Token> operations = EnumSet.of(Token.ADD, Token.MUL, Token.DIV, Token.SUB);

    private void checkOperandFlow() throws MissingArgumentException {
        if (operations.contains(currToken)) {
            throw new MissingArgumentException(expression, index);
        } else if (currToken == Token.OPEN_BRACE || currToken == Token.BEGIN) {
            throw new MissingArgumentException(expression, index);
        }
    }

    private void checkOperatorFlow() throws MissingOperatorException {
        if (currToken == Token.CLOSE_BRACE || currToken == Token.VARIABLE || currToken == Token.NUMBER) {
            throw new MissingOperatorException(expression, index);
        }
    }

    private String nextNumberString() {
        int start = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {index++;}
        int end = index--;
        return expression.substring(start, end);
    }

    private TripleExpression getUnary() throws ParsingException, OverflowException {
        nextToken();
        TripleExpression res;
        switch (currToken) {
            case NUMBER:
                res = new Const(number);
                nextToken();
                break;
            case VARIABLE:
                res = new Variable(variableName);
                nextToken();
                break;
            case NEGATE:
                res = new CheckedNegate(getUnary());
                break;
            case OPEN_BRACE:
                res = getAddAndSubtract();
                if (currToken != Token.CLOSE_BRACE) {
                    throw new MissingBracketException(expression, index);
                }
                nextToken();
                break;
            default:
                throw new MissingArgumentException(expression, index);
        }
        return res;
    }

    private TripleExpression getMultiplyAndDivision() throws ParsingException, OverflowException {
        TripleExpression res = getUnary();
        while(true) {
            switch(currToken) {
                case MUL:
                    res = new CheckedMultiply(res, getUnary());
                    break;
                case DIV:
                    res = new CheckedDivide(res, getUnary());
                    break;
                default:
                    return res;
            }
        }
    }

    private TripleExpression getAddAndSubtract() throws ParsingException, OverflowException {
        TripleExpression res = getMultiplyAndDivision();
        while(true) {
            switch(currToken) {
                case ADD:
                    res = new CheckedAdd(res, getMultiplyAndDivision());
                    break;
                case SUB:
                    res = new CheckedSubtract(res, getMultiplyAndDivision());
                    break;
                default:
                    return res;
            }
        }
    }
}
