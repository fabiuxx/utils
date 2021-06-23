/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.build.ExpressionBuilder;
import fa.gs.utils.database.query.expressions.literals.BooleanLiteral;
import fa.gs.utils.database.query.expressions.literals.CollectionLiteral;
import fa.gs.utils.database.query.expressions.literals.DateLiteral;
import fa.gs.utils.database.query.expressions.literals.Literal;
import fa.gs.utils.database.query.expressions.literals.NumberLiteral;
import fa.gs.utils.database.query.expressions.literals.StringLiteral;
import fa.gs.utils.database.query.expressions.operators.Operator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Conditions {

    public static Expression build(String leftOperand, Operator op, Boolean value) {
        Literal literal = new BooleanLiteral(value);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, String value) {
        Literal literal = new StringLiteral(value);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, Integer value) {
        Literal literal = new NumberLiteral(value);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, Long value) {
        Literal literal = new NumberLiteral(value);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, BigInteger value) {
        Literal literal = new NumberLiteral(value);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, BigDecimal value) {
        Literal literal = new NumberLiteral(value);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, Date value) {
        return build(leftOperand, op, value, DateLiteral.DateType.FECHA_HORA);
    }

    public static Expression build(String leftOperand, Operator op, Date value, DateLiteral.DateType type) {
        Literal literal = new DateLiteral(value, type);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, Integer[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, Long[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, BigInteger[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, BigDecimal[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, String[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, Date[] values) {
        return build(leftOperand, op, values, DateLiteral.DateType.FECHA_HORA);
    }

    public static Expression build(String leftOperand, Operator op, Date[] values, DateLiteral.DateType type) {
        Literal literal = CollectionLiteral.instance(values, type);
        return build(leftOperand, op, literal);
    }

    public static Expression build(String leftOperand, Operator op, Literal<?> literal) {
        ExpressionBuilder builder = ExpressionBuilder.instance();
        return builder
                .wrap(leftOperand)
                .operator(op)
                .literal(literal)
                .build();
    }

    public static Expression build(String leftOperand, Operator op, Expression part) {
        ExpressionBuilder builder = ExpressionBuilder.instance();
        return builder
                .wrap(leftOperand)
                .operator(op)
                .wrap(part)
                .build();
    }

}
