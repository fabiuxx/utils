/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.database.query.expressions.EmptyExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.JoinExpression;
import fa.gs.utils.database.query.expressions.TableExpression;
import fa.gs.utils.database.query.expressions.names.Name;
import fa.gs.utils.database.query.expressions.names.QualifiedName;
import fa.gs.utils.database.query.expressions.names.SimpleName;
import fa.gs.utils.mixins.Self;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JoinExpressionBuilder extends StackBasedExpressionBuilder implements Self<JoinExpressionBuilder> {

    private JoinExpression.Type type;
    private final ConditionsExpressionBuilder on;

    public JoinExpressionBuilder() {
        this.type = JoinExpression.Type.NORMAL;
        this.on = new ConditionsExpressionBuilder();
    }

    public JoinExpressionBuilder type(JoinExpression.Type type) {
        this.type = type;
        return self();
    }

    public JoinExpressionBuilder name(String... parts) {
        Name name = new QualifiedName(parts);
        pushName(name);
        return self();
    }

    public JoinExpressionBuilder wrap(Expression exp) {
        pushExpression(exp);
        return self();
    }

    public JoinExpressionBuilder as(String alias) {
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

    public ConditionsExpressionBuilder on() {
        consumeStack();
        return on;
    }

    @Override
    public Expression build() {
        try {
            Expression exp = output.peek();
            if (exp instanceof JoinExpression) {
                return output.pop();
            } else {
                Expression table = output.pop();
                Expression on0 = on.build();
                return JoinExpression.instance(type, table, on0);
            }
        } catch (Throwable thr) {
            return EmptyExpression.instance();
        }
    }

}
