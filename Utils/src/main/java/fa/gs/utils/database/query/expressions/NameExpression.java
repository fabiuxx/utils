/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.expressions.names.Name;

/**
 *
 * @author Fabio A. González Sosa
 */
public class NameExpression implements Expression {

    private final Name name;

    public NameExpression(Name name) {
        this.name = name;
    }

    public Name name() {
        return name;
    }

    @Override
    public String stringify(Dialect dialect) {
        return name.stringify(dialect);
    }

}
