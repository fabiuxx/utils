/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.database.query.expressions.EmptyExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.Operators;
import fa.gs.utils.database.query.expressions.literals.BooleanLiteral;
import fa.gs.utils.database.query.expressions.literals.CollectionLiteral;
import fa.gs.utils.database.query.expressions.literals.DateLiteral;
import fa.gs.utils.database.query.expressions.literals.Literal;
import fa.gs.utils.database.query.expressions.literals.NullLiteral;
import fa.gs.utils.database.query.expressions.literals.NumberLiteral;
import fa.gs.utils.database.query.expressions.literals.RawLiteral;
import fa.gs.utils.database.query.expressions.literals.StringLiteral;
import fa.gs.utils.database.query.expressions.names.Name;
import fa.gs.utils.database.query.expressions.names.QualifiedName;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.mixins.Self;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class AbstractExpressionBuilder<T extends AbstractExpressionBuilder<T>> extends StackBasedExpressionBuilder implements Self<T>, ExpressionStep<T> {

    @Override
    public Expression build() {
        try {
            consumeStack();
            Expression exp = popExpression();
            return (exp == null) ? EmptyExpression.instance() : exp;
        } catch (Throwable thr) {
            return EmptyExpression.instance();
        }
    }

    @Override
    public T wrap(Expression exp) {
        pushExpression(exp);
        return self();
    }

    @Override
    public T raw(String fmt, Object... args) {
        Literal L = new RawLiteral(Strings.format(fmt, args));
        pushLiteral(L);
        return self();
    }

    //<editor-fold defaultstate="collapsed" desc="Grouping Operators">
    @Override
    public T lpar() {
        pushOperator(Operators.LEFT_PAR);
        return self();
    }

    @Override
    public T rpar() {
        pushOperator(Operators.RIGHT_PAR);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Arithmetic Operators">
    @Override
    public T pow() {
        pushOperator(Operators.POW);
        return self();
    }

    @Override
    public T mod() {
        pushOperator(Operators.MOD);
        return self();
    }

    @Override
    public T mul() {
        pushOperator(Operators.MUL);
        return self();
    }

    @Override
    public T div() {
        pushOperator(Operators.DIV);
        return self();
    }

    @Override
    public T add() {
        pushOperator(Operators.ADD);
        return self();
    }

    @Override
    public T sub() {
        pushOperator(Operators.SUB);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Boolean Operators">
    @Override
    public T not() {
        pushOperator(Operators.NOT);
        return self();
    }

    @Override
    public T and() {
        pushOperator(Operators.AND);
        return self();
    }

    @Override
    public T or() {
        pushOperator(Operators.OR);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Comparision Operators">
    @Override
    public T equals() {
        pushOperator(Operators.EQUALS);
        return self();
    }

    @Override
    public T notEquals() {
        pushOperator(Operators.NOT_EQUALS);
        return self();
    }

    @Override
    public T less() {
        pushOperator(Operators.LESS);
        return self();
    }

    @Override
    public T lessEquals() {
        pushOperator(Operators.LESS_EQUAL);
        return self();
    }

    @Override
    public T greater() {
        pushOperator(Operators.GREATER);
        return self();
    }

    @Override
    public T greaterEquals() {
        pushOperator(Operators.GREATER_EQUAL);
        return self();
    }

    @Override
    public T like() {
        pushOperator(Operators.LIKE);
        return self();
    }

    @Override
    public T ilike() {
        pushOperator(Operators.ILIKE);
        return self();
    }

    @Override
    public T notLike() {
        pushOperator(Operators.NOT_LIKE);
        return self();
    }

    @Override
    public T notIlike() {
        pushOperator(Operators.NOT_ILIKE);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constants">
    @Override
    public T TRUE() {
        return literal(Boolean.TRUE);
    }

    @Override
    public T FALSE() {
        return literal(Boolean.FALSE);
    }

    @Override
    public T NULL() {
        Literal literal = new NullLiteral();
        pushLiteral(literal);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Names">
    @Override
    public T name(String... parts) {
        Name name = new QualifiedName(parts);
        pushName(name);
        return self();
    }

    @Override
    public T name(Name name) {
        pushName(name);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Text Literals">
    @Override
    public T literal(String value) {
        Literal literal = new StringLiteral(value);
        pushLiteral(literal);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Boolean Literals">
    @Override
    public T literal(boolean value) {
        Literal lit = new BooleanLiteral(value);
        pushLiteral(lit);
        return self();
    }

    @Override
    public T literal(Boolean value) {
        Literal lit = new BooleanLiteral(value);
        pushLiteral(lit);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Numeric Literals">
    @Override
    public T literal(int value) {
        Literal lit = new NumberLiteral(value);
        pushLiteral(lit);
        return self();
    }

    @Override
    public T literal(Integer value) {
        Literal lit = new NumberLiteral(value);
        pushLiteral(lit);
        return self();
    }

    @Override
    public T literal(long value) {
        Literal lit = new NumberLiteral(value);
        pushLiteral(lit);
        return self();
    }

    @Override
    public T literal(Long value) {
        Literal lit = new NumberLiteral(value);
        pushLiteral(lit);
        return self();
    }

    @Override
    public T literal(BigInteger value) {
        Literal lit = new NumberLiteral(value);
        pushLiteral(lit);
        return self();
    }

    @Override
    public T literal(BigDecimal value) {
        Literal lit = new NumberLiteral(value);
        pushLiteral(lit);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Date Literals">
    @Override
    public T literal(Date value) {
        Literal lit = new DateLiteral(value);
        pushLiteral(lit);
        return self();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Collection Literals">
    public T in(Integer... values) {
        pushOperator(Operators.IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T in(Long... values) {
        pushOperator(Operators.IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T in(BigInteger... values) {
        pushOperator(Operators.IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T in(BigDecimal... values) {
        pushOperator(Operators.IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T in(String... values) {
        pushOperator(Operators.IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T in(Date... values) {
        pushOperator(Operators.IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T notIn(Integer... values) {
        pushOperator(Operators.NOT_IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T notIn(Long... values) {
        pushOperator(Operators.NOT_IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T notIn(BigInteger... values) {
        pushOperator(Operators.NOT_IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T notIn(BigDecimal... values) {
        pushOperator(Operators.NOT_IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T notIn(String... values) {
        pushOperator(Operators.NOT_IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }

    public T notIn(Date... values) {
        pushOperator(Operators.NOT_IN);
        pushLiteral(CollectionLiteral.instance(values));
        return self();
    }
    //</editor-fold>

}
