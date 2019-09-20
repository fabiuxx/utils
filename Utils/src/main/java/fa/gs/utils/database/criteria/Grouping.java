/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria;

import fa.gs.utils.database.sql.format.GroupingFormatter;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Grouping implements Serializable {

    private final String expression;

    public Grouping(String expression) {
        this.expression = expression;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getExpression() {
        return expression;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return GroupingFormatter.toString(this);
    }

}
