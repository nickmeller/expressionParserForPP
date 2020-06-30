package expression;

public class Const implements TripleExpression {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
    }


    public boolean equals(Object other) {
        return other instanceof Const && this.value == ((Const) other).value;
    }
    
    public int hashCode() {
        return toString().hashCode();
    }
}
