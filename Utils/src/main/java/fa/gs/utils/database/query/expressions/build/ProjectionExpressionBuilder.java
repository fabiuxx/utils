/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.database.query.expressions.AliasExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.names.Name;
import fa.gs.utils.database.query.expressions.names.QualifiedName;
import fa.gs.utils.database.query.expressions.names.SimpleName;
import fa.gs.utils.misc.Assertions;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ProjectionExpressionBuilder extends AbstractExpressionBuilder<ProjectionExpressionBuilder> implements ProjectionStep<ProjectionExpressionBuilder> {

    @Override
    public ProjectionExpressionBuilder all(String... parts0) {
        String[] parts;
        if (Assertions.isNullOrEmpty(parts0)) {
            parts = new String[]{"*"};
        } else {
            parts = new String[parts0.length + 1];
            System.arraycopy(parts0, 0, parts, 0, parts0.length);
            parts[parts0.length] = "*";
        }

        Name name = new QualifiedName(parts);
        return all(name);
    }

    @Override
    public ProjectionExpressionBuilder all(Name name) {
        pushName(name);
        return self();
    }

    @Override
    public ProjectionExpressionBuilder as(String alias) {
        // Nombre alias.
        Name name = new SimpleName(alias);
        return as(name);
    }

    @Override
    public ProjectionExpressionBuilder as(Name name) {
        // Materializar expresion que sirve como operando.
        consumeStack();
        Expression exp = popExpression();

        // Expresion de alias.
        Expression aliasExpression = new AliasExpression(exp, name);
        pushExpression(aliasExpression);

        return self();
    }

}
