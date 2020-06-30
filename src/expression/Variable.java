package expression;

public class Variable implements TripleExpression {
    private final String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (varName.equals("x"))
            return x;
        else if (varName.equals("y"))
            return y;
        else return z;
    }

    public String toString() {
        return varName;
    }

    public boolean equals(Object other) {
        return other instanceof Variable
                && this.varName.equals(((Variable) other).varName);
    }

    public int hashCode() {
        return toString().hashCode();
    }
}
