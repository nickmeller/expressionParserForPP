package expression;

import expression.exceptions.EvaluatingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression extends Expression {
    int evaluate(int x, int y, int z) throws EvaluatingException;
    default int evaluate(int x) throws EvaluatingException {
        return evaluate(x, x, x);
    }
}
