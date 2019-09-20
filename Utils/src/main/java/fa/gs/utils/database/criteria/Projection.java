/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria;

import fa.gs.utils.database.sql.format.ProjectionFormatter;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Projection implements Serializable {

    private final String projection;
    private final String as;

    public Projection(String projection, String as) {
        this.projection = projection;
        this.as = as;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getProjection() {
        return projection;
    }

    public String getAs() {
        return as;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return ProjectionFormatter.toString(this);
    }

}
