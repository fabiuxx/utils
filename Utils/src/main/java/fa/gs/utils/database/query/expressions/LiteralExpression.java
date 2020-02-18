/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.expressions.literals.Literal;

/**
 *
 * @author Fabio A. González Sosa
 */
public class LiteralExpression implements Expression {

    private final Literal literal;

    public LiteralExpression(Literal literal) {
        this.literal = literal;
    }

    public Literal literal() {
        return literal;
    }

    @Override
    public String stringify(Dialect dialect) {
        return literal.stringify(dialect);
    }

}
