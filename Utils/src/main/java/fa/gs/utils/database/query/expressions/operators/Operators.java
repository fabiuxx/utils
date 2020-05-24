/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.operators;

import fa.gs.utils.database.query.Dialect;

/**
 *
 * @author Fabio A. González Sosa
 */
public enum Operators implements Operator {
    LEFT_PAR(Precedences.L00, Cardinalities.NONE, "("),
    RIGHT_PAR(Precedences.L00, Cardinalities.NONE, ")"),
    POW(Precedences.L03, Cardinalities.BINARY, "^"),
    MUL(Precedences.L04, Cardinalities.BINARY, "*"),
    DIV(Precedences.L04, Cardinalities.BINARY, "/"),
    MOD(Precedences.L04, Cardinalities.BINARY, "%"),
    CONCAT(Precedences.L05, Cardinalities.BINARY, "||"),
    ADD(Precedences.L05, Cardinalities.BINARY, "+"),
    SUB(Precedences.L05, Cardinalities.BINARY, "-"),
    IS(Precedences.L06, Cardinalities.BINARY, "IS"),
    IS_NULL(Precedences.L07, Cardinalities.UNARY, "IS NULL"),
    NOT_NULL(Precedences.L08, Cardinalities.UNARY, "NOT NULL"),
    IN(Precedences.L09, Cardinalities.BINARY, "IN"),
    NOT_IN(Precedences.L09, Cardinalities.BINARY, "NOT IN"),
    LIKE(Precedences.L10, Cardinalities.BINARY, "LIKE"),
    NOT_LIKE(Precedences.L10, Cardinalities.BINARY, "NOT LIKE"),
    ILIKE(Precedences.L10, Cardinalities.BINARY, "ILIKE"),
    NOT_ILIKE(Precedences.L10, Cardinalities.BINARY, "NOT ILIKE"),
    EQUALS(Precedences.L11, Cardinalities.BINARY, "="),
    NOT_EQUALS(Precedences.L11, Cardinalities.BINARY, "<>"),
    LESS(Precedences.L11, Cardinalities.BINARY, "<"),
    LESS_EQUAL(Precedences.L11, Cardinalities.BINARY, "<="),
    GREATER(Precedences.L11, Cardinalities.BINARY, ">"),
    GREATER_EQUAL(Precedences.L11, Cardinalities.BINARY, ">="),
    NOT(Precedences.L12, Cardinalities.UNARY, "NOT"),
    AND(Precedences.L13, Cardinalities.BINARY, "AND"),
    OR(Precedences.L14, Cardinalities.BINARY, "OR");
    private final int precedence;
    private final int cardinality;
    private final String symbol;

    Operators(int precedence, int cardinality, String symbol) {
        this.precedence = precedence;
        this.cardinality = cardinality;
        this.symbol = symbol;
    }

    @Override
    public String symbol() {
        return symbol;
    }

    @Override
    public int precedence() {
        return precedence;
    }

    @Override
    public int associativity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cardinality() {
        return cardinality;
    }

    @Override
    public String stringify(Dialect dialect) {
        return symbol;
    }

    public static class Precedences {

        private static final int L00 = 0;
        private static final int L01 = 1;
        private static final int L02 = 2;
        private static final int L03 = 3;
        private static final int L04 = 4;
        private static final int L05 = 5;
        private static final int L06 = 6;
        private static final int L07 = 7;
        private static final int L08 = 8;
        private static final int L09 = 9;
        private static final int L10 = 10;
        private static final int L11 = 11;
        private static final int L12 = 12;
        private static final int L13 = 13;
        private static final int L14 = 14;
    }

    public static class Associativities {

        public static final int LEFT = 0;
        public static final int RIGHT = 1;
    }

    public static class Cardinalities {

        public static final int NONE = 0;
        public static final int UNARY = 1;
        public static final int BINARY = 2;
        public static final int TERCIARY = 3;
    }

}
