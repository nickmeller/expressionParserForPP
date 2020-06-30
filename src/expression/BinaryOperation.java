package expression;

import expression.exceptions.EvaluatingException;

public abstract class BinaryOperation implements TripleExpression {
    private final TripleExpression left;
    private final TripleExpression right;
    protected String sign;

    BinaryOperation(TripleExpression left, TripleExpression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int operationImpl(int left, int right) throws EvaluatingException;

    protected abstract void check(int left, int right) throws EvaluatingException;

    public String toString() {
        return "(" + left.toMiniString() + " " + sign + " " + right.toMiniString() + ")";
    }

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return operationImpl(
                left.evaluate(x, y, z),
                right.evaluate(x, y, z)
        );
    }

    public boolean equals(Object other) {
        return other != null &&
                this.getClass() == other.getClass() &&
                left.equals(((BinaryOperation) other).left) &&
                right.equals(((BinaryOperation) other).right);
    }

    public int hashCode() {;
        return (119 * left.hashCode() + 713) * 521 + 19 * right.hashCode() + 313 * sign.hashCode();
    }
}
