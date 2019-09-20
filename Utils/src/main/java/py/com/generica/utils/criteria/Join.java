/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria;

import java.io.Serializable;
import py.com.generica.utils.criteria.sql.format.JoinFormatter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Join implements Serializable {

    private final Projection projection;
    private final Condition joinContidion;
    private final JoinKind kind;

    public Join(JoinKind kind, Projection projection, Condition condition) {
        this.kind = kind;
        this.projection = projection;
        this.joinContidion = condition;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public Projection getProjection() {
        return projection;
    }

    public Condition getJoinContidion() {
        return joinContidion;
    }

    public JoinKind getKind() {
        return kind;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return JoinFormatter.toString(this);
    }

}
