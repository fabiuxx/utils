/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JoinExpression implements Expression {

    private final Type type;
    private final Expression table;
    private final Expression on;

    public JoinExpression(Type type, Expression table, Expression on) {
        this.type = type;
        this.table = table;
        this.on = on;
    }

    public static Expression instance(Expression table) {
        return instance(Type.NORMAL, table);
    }

    public static Expression instance(Type type, Expression table) {
        return instance(type, table, null);
    }

    public static Expression instance(Type type, Expression table, Expression on) {
        return new JoinExpression(type, table, on);
    }

    public Type type() {
        return type;
    }

    public Expression table() {
        return table;
    }

    public Expression on() {
        return on;
    }

    @Override
    public String stringify(Dialect dialect) {
        if (on != null && (on instanceof EmptyExpression) == false) {
            return Strings.format("%s %s ON %s", type.keyword, table.stringify(dialect), on.stringify(dialect));
        } else {
            return Strings.format("%s %s", type.keyword, table.stringify(dialect));
        }
    }

    public static enum Type {
        NORMAL("JOIN"),
        INNER("INNER JOIN"),
        LEFT("LEFT JOIN"),
        RIGHT("RIGHT JOIN"),
        LATERAL("LATERAL JOIN");
        private final String keyword;

        Type(String keyword) {
            this.keyword = keyword;
        }

    }

}
