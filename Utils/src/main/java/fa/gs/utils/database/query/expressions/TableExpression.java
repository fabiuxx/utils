/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.expressions.names.Name;
import fa.gs.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class TableExpression implements Expression {

    private final Expression table;
    private final Name alias;

    TableExpression(Expression table, Name alias) {
        this.table = table;
        this.alias = alias;
    }

    public static Expression instance(Expression table) {
        return instance(table, null);
    }

    public static Expression instance(Expression table, Name name) {
        return new TableExpression(table, name);
    }

    @Override
    public String stringify(Dialect dialect) {
        if (alias != null) {
            return Strings.format("%s AS %s", table.stringify(dialect), alias.stringify(dialect));
        } else {
            return Strings.format("%s", table.stringify(dialect));
        }
    }

}
