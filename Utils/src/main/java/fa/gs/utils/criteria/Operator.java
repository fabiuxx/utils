/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria;

import java.io.Serializable;

/**
 * Tipos de operadores soportados para los campos de filtro.
 *
 * @author Fabio A. González Sosa
 */
public enum Operator implements Serializable {
    EQUALS("eq", "="),
    NOT_EQUALS("neq", "!="),
    LIKE("like", "like"),
    IN("in", "in"),
    IS("is", "is"),
    GREATER("get", ">"),
    GREATER_EQUAL("geq", ">="),
    LESS("let", "<"),
    LESS_EQUAL("leq", "<="),
    OR("or", "or"),
    AND("and", "and");
    final String value;
    final String symbol;

    Operator(String value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public static Operator from(String text) {
        for (Operator operator : Operator.values()) {
            if (operator.symbol.equalsIgnoreCase(text) || operator.value.equalsIgnoreCase(text)) {
                return operator;
            }
        }
        return null;
    }

    public String symbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return value;
    }

}
