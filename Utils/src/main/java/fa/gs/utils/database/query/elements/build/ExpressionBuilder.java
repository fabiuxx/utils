/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.build;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.utils.Expressions;
import fa.gs.utils.database.query.expressions.literals.BooleanLiteral;
import fa.gs.utils.database.query.expressions.literals.CollectionLiteral;
import fa.gs.utils.database.query.expressions.literals.DateLiteral;
import fa.gs.utils.database.query.expressions.literals.Literal;
import fa.gs.utils.database.query.expressions.literals.NullLiteral;
import fa.gs.utils.database.query.expressions.literals.NumberLiteral;
import fa.gs.utils.database.query.expressions.literals.RawLiteral;
import fa.gs.utils.database.query.expressions.literals.StringLiteral;
import fa.gs.utils.database.query.expressions.operators.Operators;
import fa.gs.utils.misc.text.StringBuilder2;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.mixins.Self;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ExpressionBuilder implements Self<ExpressionBuilder> {

    private final Collection<QueryPart> parts;

    ExpressionBuilder() {
        this.parts = Lists.empty();
    }

    public static ExpressionBuilder instance() {
        return new ExpressionBuilder();
    }

    private void push(QueryPart part) {
        if (part != null) {
            this.parts.add(part);
        }
    }

    public Expression build(final Dialect dialect) {
        final StringBuilder2 builder = new StringBuilder2();
        for (QueryPart part : parts) {
            String exp0 = part.stringify(dialect);
            builder.append(" %s ", exp0);
        }

        return Expressions.build(dialect, builder.toString());
    }

    public ExpressionBuilder wrap(Expression exp) {
        push(exp);
        return self();
    }

    public ExpressionBuilder wrap(String fmt, Object... args) {
        push(new RawLiteral(Strings.format(fmt, args)));
        return self();
    }

    //<editor-fold publicstate="collapsed" desc="Grouping Operators">
    public ExpressionBuilder lpar() {
        push(Operators.LEFT_PAR);
        return self();
    }

    public ExpressionBuilder rpar() {
        push(Operators.RIGHT_PAR);
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Arithmetic Operators">
    public ExpressionBuilder pow() {
        push(Operators.POW);
        return self();
    }

    public ExpressionBuilder mod() {
        push(Operators.MOD);
        return self();
    }

    public ExpressionBuilder mul() {
        push(Operators.MUL);
        return self();
    }

    public ExpressionBuilder div() {
        push(Operators.DIV);
        return self();
    }

    public ExpressionBuilder add() {
        push(Operators.ADD);
        return self();
    }

    public ExpressionBuilder sub() {
        push(Operators.SUB);
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Boolean Operators">
    public ExpressionBuilder not() {
        push(Operators.NOT);
        return self();
    }

    public ExpressionBuilder and() {
        push(Operators.AND);
        return self();
    }

    public ExpressionBuilder or() {
        push(Operators.OR);
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Comparision Operators">
    public ExpressionBuilder equals() {
        push(Operators.EQUALS);
        return self();
    }

    public ExpressionBuilder notEquals() {
        push(Operators.NOT_EQUALS);
        return self();
    }

    public ExpressionBuilder less() {
        push(Operators.LESS);
        return self();
    }

    public ExpressionBuilder lessEquals() {
        push(Operators.LESS_EQUAL);
        return self();
    }

    public ExpressionBuilder greater() {
        push(Operators.GREATER);
        return self();
    }

    public ExpressionBuilder greaterEquals() {
        push(Operators.GREATER_EQUAL);
        return self();
    }

    public ExpressionBuilder like() {
        push(Operators.LIKE);
        return self();
    }

    public ExpressionBuilder ilike() {
        push(Operators.ILIKE);
        return self();
    }

    public ExpressionBuilder notLike() {
        push(Operators.NOT_LIKE);
        return self();
    }

    public ExpressionBuilder notIlike() {
        push(Operators.NOT_ILIKE);
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Constants">
    public ExpressionBuilder TRUE() {
        return literal(Boolean.TRUE);
    }

    public ExpressionBuilder FALSE() {
        return literal(Boolean.FALSE);
    }

    public ExpressionBuilder NULL() {
        push(new NullLiteral());
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Names">
    public ExpressionBuilder name(String... parts) {
        push(new Name(parts));
        return self();
    }

    public ExpressionBuilder name(Name name) {
        push(name);
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Text Literals">
    public ExpressionBuilder literal(String value) {
        push(new StringLiteral(value));
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Boolean Literals">
    public ExpressionBuilder literal(boolean value) {
        push(new BooleanLiteral(value));
        return self();
    }

    public ExpressionBuilder literal(Boolean value) {
        push(new BooleanLiteral(value));
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Numeric Literals">
    public ExpressionBuilder literal(int value) {
        push(new NumberLiteral(value));
        return self();
    }

    public ExpressionBuilder literal(Integer value) {
        push(new NumberLiteral(value));
        return self();
    }

    public ExpressionBuilder literal(long value) {
        push(new NumberLiteral(value));
        return self();
    }

    public ExpressionBuilder literal(Long value) {
        push(new NumberLiteral(value));
        return self();
    }

    public ExpressionBuilder literal(BigInteger value) {
        push(new NumberLiteral(value));
        return self();
    }

    public ExpressionBuilder literal(BigDecimal value) {
        push(new NumberLiteral(value));
        return self();
    }

    public ExpressionBuilder literal(Number value) {
        push(new NumberLiteral(value));
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Date Literals">
    public ExpressionBuilder literal(Date value) {
        return literal(DateLiteral.DateType.FECHA_HORA, value);
    }

    public ExpressionBuilder literal(DateLiteral.DateType dateType, Date value) {
        push(new DateLiteral(value, dateType));
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Any Literal">
    public ExpressionBuilder literal(Literal<?> value) {
        push(value);
        return self();
    }
    //</editor-fold>

    //<editor-fold publicstate="collapsed" desc="Collection Literals">
    public ExpressionBuilder in(Integer... values) {
        push(Operators.IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder in(Long... values) {
        push(Operators.IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder in(BigInteger... values) {
        push(Operators.IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder in(BigDecimal... values) {
        push(Operators.IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder in(String... values) {
        push(Operators.IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder in(Date... values) {
        return in(DateLiteral.DateType.FECHA_HORA, values);
    }

    public ExpressionBuilder in(DateLiteral.DateType dateType, Date... values) {
        push(Operators.IN);
        push(CollectionLiteral.instance(dateType, values));
        return self();
    }

    public ExpressionBuilder notIn(Integer... values) {
        push(Operators.NOT_IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder notIn(Long... values) {
        push(Operators.NOT_IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder notIn(BigInteger... values) {
        push(Operators.NOT_IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder notIn(BigDecimal... values) {
        push(Operators.NOT_IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder notIn(String... values) {
        push(Operators.NOT_IN);
        push(CollectionLiteral.instance(values));
        return self();
    }

    public ExpressionBuilder notIn(Date... values) {
        return notIn(DateLiteral.DateType.FECHA_HORA, values);
    }

    public ExpressionBuilder notIn(DateLiteral.DateType dateType, Date... values) {
        push(Operators.NOT_IN);
        push(CollectionLiteral.instance(dateType, values));
        return self();
    }
    //</editor-fold>

}
