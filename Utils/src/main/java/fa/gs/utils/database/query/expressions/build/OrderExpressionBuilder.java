/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.database.query.expressions.EmptyExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.OrderExpression;
import fa.gs.utils.database.query.expressions.names.Name;
import fa.gs.utils.database.query.expressions.names.QualifiedName;
import fa.gs.utils.mixins.Self;

/**
 *
 * @author Fabio A. González Sosa
 */
public class OrderExpressionBuilder extends StackBasedExpressionBuilder implements Self<OrderExpressionBuilder> {

    private OrderExpression.Type type;

    OrderExpressionBuilder() {
        this.type = OrderExpression.Type.ASC;
    }

    public static OrderExpressionBuilder instance() {
        return new OrderExpressionBuilder();
    }

    public OrderExpressionBuilder name(String... parts) {
        Name name = new QualifiedName(parts);
        pushName(name);
        return self();
    }

    public OrderExpressionBuilder wrap(Expression exp) {
        pushExpression(exp);
        return self();
    }

    public OrderExpressionBuilder asc() {
        type = OrderExpression.Type.ASC;
        return self();
    }

    public OrderExpressionBuilder desc() {
        type = OrderExpression.Type.DESC;
        return self();
    }

    @Override
    public Expression build() {
        try {
            consumeStack();
            Expression criteria = output.pop();
            return OrderExpression.instance(criteria, type);
        } catch (Throwable thr) {
            return EmptyExpression.instance();
        }
    }

}
