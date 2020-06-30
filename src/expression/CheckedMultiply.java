package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedMultiply extends BinaryOperation{
    public CheckedMultiply(TripleExpression left, TripleExpression right) {
        super(left, right);
        sign = "*";
    }

    @Override
    protected int operationImpl(int left, int right) throws EvaluatingException {
        check(left, right);
        return left * right;
    }

    @Override
    protected void check(int left, int right) throws EvaluatingException {
        if (left > 0 && right > 0 && Integer.MAX_VALUE / left < right) {
            throw new OverflowException();
        }
        if (left > 0 && right < 0 && Integer.MIN_VALUE / left > right) {
            throw new OverflowException();
        }
        if (left < 0 && right > 0 && Integer.MIN_VALUE / right > left) {
            throw new OverflowException();
        }
        if (left < 0 && right < 0 && Integer.MAX_VALUE / left > right) {
            throw new OverflowException();
        }
    }


}
