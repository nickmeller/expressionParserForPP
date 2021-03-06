package expression;

import expression.exceptions.*;

public class CheckedDivide extends BinaryOperation{

    public CheckedDivide(TripleExpression left, TripleExpression right) {
        super(left, right);
        sign = "/";
    }

    @Override
    protected int operationImpl(int left, int right) throws EvaluatingException {
        check(left, right);
        return left / right;
    }

    @Override
    protected void check(int left, int right) throws EvaluatingException {
        if (right == 0) {
            throw new IllegalOperationException("division be zero");
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new OverflowException();
        }
    }
}
