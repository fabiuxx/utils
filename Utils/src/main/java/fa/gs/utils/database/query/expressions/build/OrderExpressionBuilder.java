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

    public OrderExpressionBuilder type(OrderExpression.Type type) {
        this.type = type;
        return self();
    }

    public OrderExpressionBuilder asc() {
        return type(OrderExpression.Type.ASC);
    }

    public OrderExpressionBuilder desc() {
        return type(OrderExpression.Type.DESC);
    }

    @Override
    public Expression build() {
        try {
            consumeStack();
            Expression exp = popExpression();
            return (exp == null) ? EmptyExpression.instance() : exp;
        } catch (Throwable thr) {
            return EmptyExpression.instance();
        }
    }

}
