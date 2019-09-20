/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria;

import java.io.Serializable;
import py.com.generica.utils.criteria.sql.format.SortingFormatter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Sorting implements Serializable {

    private final String expression;
    private final OrderKind order;

    public Sorting(String expression, OrderKind order) {
        this.expression = expression;
        this.order = order;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getExpression() {
        return expression;
    }

    public OrderKind getOrder() {
        return order;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return SortingFormatter.toString(this);
    }

}
