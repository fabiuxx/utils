/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria;

import java.io.Serializable;
import py.com.generica.utils.criteria.sql.format.ConditionFormatter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Condition implements Serializable {

    private final String lexpression;
    private final Operator operator;
    private final Object rexpression;

    public Condition(String lexpression, Operator operator, Object rexpression) {
        this.lexpression = lexpression;
        this.operator = operator;
        this.rexpression = rexpression;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getLeftExpression() {
        return lexpression;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getRightExpression() {
        return rexpression;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return ConditionFormatter.toString(this);
    }

}
