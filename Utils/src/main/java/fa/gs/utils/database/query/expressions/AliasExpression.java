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
public class AliasExpression extends BinaryExpression {

    public AliasExpression(Expression leftOperand, Name name) {
        super(leftOperand, Operators.AS, new NameExpression(name));
    }

    @Override
    public String stringify(Dialect dialect) {
        return Strings.format("(%s) %s %s", leftOperand().stringify(dialect), operator().stringify(dialect), rightOperand().stringify(dialect));
    }

}
