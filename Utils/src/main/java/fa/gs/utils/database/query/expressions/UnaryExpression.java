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
public class UnaryExpression implements Expression {

    private final Expression operand;
    private final Operator operator;

    public UnaryExpression(Operator operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public Expression operand() {
        return operand;
    }

    public Operator operator() {
        return operator;
    }

    @Override
    public String stringify(Dialect dialect) {
        // TODO: CORREGIR ORDEN DE ACUERDO A ASOCIATIVIDAD DE OPERADOR. POR DEFECTO SE MUESTRA EL OPERADOR ANTES QUE EL OPERANDO.
        return Strings.format("(%s %s)", operator.stringify(dialect), operand.stringify(dialect));
        //return Strings.format("(%s %s)", operand.stringify(dialect), operator.stringify(dialect));
    }

}
