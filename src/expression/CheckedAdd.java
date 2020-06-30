package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedAdd extends BinaryOperation {

    public CheckedAdd(TripleExpression left, TripleExpression right) {
        super(left, right);
        sign = "+";
    }

    protected int operationImpl(int left, int right) throws EvaluatingException {
        check(left, right);
        return left + right;
    }

    @Override
    protected void check(int left, int right) throws EvaluatingException {
        if (left > 0 && right > Integer.MAX_VALUE - left) {
            throw new OverflowException();
        }
        if (left < 0 && right < Integer.MIN_VALUE - left) {
            throw new OverflowException();
        }
    }
}
