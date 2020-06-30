package expression;

import expression.exceptions.EvaluatingException;

public abstract class UnaryOperation implements TripleExpression{
    private final TripleExpression operand;

    protected abstract int operationImpl(int operand) throws EvaluatingException;

    protected abstract void check(int value) throws EvaluatingException;

    protected String sign;

    UnaryOperation(TripleExpression operand) {
        this.operand = operand;
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return operationImpl(operand.evaluate(x, y, z));
    }

    @Override
    public int hashCode() {
        return 11 + operand.hashCode() * 113 + 123 * sign.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj.getClass() == this.getClass() &&
                this.operand.equals(((UnaryOperation) obj).operand);
    }

    @Override
    public String toString() {
        return sign + " " + operand.toString();
    }
}
