/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.database.query.expressions.Expression;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface ExpressionStep<T extends ExpressionStep<T>> extends NameStep<T> {

    T wrap(Expression exp);

    T raw(String fmt, Object... args);

    //<editor-fold defaultstate="collapsed" desc="Grouping Operators">
    T lpar();

    T rpar();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Arithmetic Operators">
    T pow();

    T mod();

    T mul();

    T div();

    T add();

    T sub();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Boolean Operators">
    T not();

    T and();

    T or();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Comparision Operators">
    T equals();

    T notEquals();

    T less();

    T lessEquals();

    T greater();

    T greaterEquals();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constants">
    T TRUE();

    T FALSE();

    T NULL();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Text Literals">
    T literal(String value);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Boolean Literals">
    T literal(boolean value);

    T literal(Boolean value);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Numeric Literals">
    T literal(int value);

    T literal(Integer value);

    T literal(long value);

    T literal(Long value);

    T literal(BigInteger value);

    T literal(BigDecimal value);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Date Literals">
    T literal(Date value);
    //</editor-fold>

}
