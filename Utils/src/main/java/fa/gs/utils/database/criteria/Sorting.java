/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria;

import fa.gs.utils.database.sql.format.SortingFormatter;
import java.io.Serializable;

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
