package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedNegate extends UnaryOperation{

    public CheckedNegate(TripleExpression operand) {
        super(operand);
        sign = "-";
    }

    @Override
    protected int operationImpl(int operand) throws EvaluatingException {
        check(operand);
        return -operand;
    }

    protected void check(int value) throws EvaluatingException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }
}
