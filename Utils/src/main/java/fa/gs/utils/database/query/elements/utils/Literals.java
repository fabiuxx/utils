/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.expressions.literals.BooleanLiteral;
import fa.gs.utils.database.query.expressions.literals.CollectionLiteral;
import fa.gs.utils.database.query.expressions.literals.DateLiteral;
import fa.gs.utils.database.query.expressions.literals.Literal;
import fa.gs.utils.database.query.expressions.literals.NumberLiteral;
import fa.gs.utils.database.query.expressions.literals.StringLiteral;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Literals {

    public static Literal build(Boolean value) {
        Literal literal = new BooleanLiteral(value);
        return literal;
    }

    public static Literal build(String value) {
        Literal literal = new StringLiteral(value);
        return literal;
    }

    public static Literal build(Integer value) {
        Literal literal = new NumberLiteral(value);
        return literal;
    }

    public static Literal build(Long value) {
        Literal literal = new NumberLiteral(value);
        return literal;
    }

    public static Literal build(BigInteger value) {
        Literal literal = new NumberLiteral(value);
        return literal;
    }

    public static Literal build(BigDecimal value) {
        Literal literal = new NumberLiteral(value);
        return literal;
    }

    public static Literal build(Date value) {
        return build(value, DateLiteral.DateType.FECHA_HORA);
    }

    public static Literal build(Date value, DateLiteral.DateType type) {
        Literal literal = new DateLiteral(value, type);
        return literal;
    }

    public static Literal build(Integer[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return literal;
    }

    public static Literal build(Long[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return literal;
    }

    public static Literal build(BigInteger[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return literal;
    }

    public static Literal build(BigDecimal[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return literal;
    }

    public static Literal build(String[] values) {
        Literal literal = CollectionLiteral.instance(values);
        return literal;
    }

    public static Literal build(Date[] values) {
        return build(values, DateLiteral.DateType.FECHA_HORA);
    }

    public static Literal build(Date[] values, DateLiteral.DateType type) {
        Literal literal = CollectionLiteral.instance(values, type);
        return literal;
    }

}
