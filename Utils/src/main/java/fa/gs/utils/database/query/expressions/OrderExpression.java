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
public class OrderExpression implements Expression {

    private final OrderExpression.Type type;
    private final Expression criteria;

    OrderExpression(Expression criteria, OrderExpression.Type type) {
        this.criteria = criteria;
        this.type = type;
    }

    public static Expression instance(Expression criteria) {
        return instance(criteria, Type.ASC);
    }

    public static Expression instance(Expression criteria, OrderExpression.Type type) {
        return new OrderExpression(criteria, type);
    }

    @Override
    public String stringify(Dialect dialect) {
        return Strings.format("%s %s", criteria.stringify(dialect), type.keyword);
    }

    public static enum Type {
        ASC("ASC"),
        DESC("DESC");
        private final String keyword;

        Type(String keyword) {
            this.keyword = keyword;
        }
    }

}
