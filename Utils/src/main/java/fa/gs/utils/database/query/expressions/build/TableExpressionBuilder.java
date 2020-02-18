/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.database.query.expressions.EmptyExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.TableExpression;
import fa.gs.utils.database.query.expressions.names.Name;
import fa.gs.utils.database.query.expressions.names.QualifiedName;
import fa.gs.utils.database.query.expressions.names.SimpleName;
import fa.gs.utils.mixins.Self;

/**
 *
 * @author Fabio A. González Sosa
 */
public class TableExpressionBuilder extends StackBasedExpressionBuilder implements Self<TableExpressionBuilder> {

    public TableExpressionBuilder name(String... parts) {
        Name name = new QualifiedName(parts);
        pushName(name);
        return self();
    }

    public TableExpressionBuilder wrap(Expression exp) {
        pushExpression(exp);
        return self();
    }

    public TableExpressionBuilder as(String alias) {
        // Materializar expresion que sirve como operando.
        consumeStack();
        Expression exp = popExpression();

        // Nombre alias.
        Name name = new SimpleName(alias);

        // Expresion de alias.
        Expression tableExpression = TableExpression.instance(exp, name);
        pushExpression(tableExpression);

        return self();
    }

    @Override
    public Expression build() {
        try {
            consumeStack();
            return output.pop();
        } catch (Throwable thr) {
            return EmptyExpression.instance();
        }
    }

}
