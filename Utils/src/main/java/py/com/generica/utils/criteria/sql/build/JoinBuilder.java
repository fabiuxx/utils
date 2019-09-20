/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.build;

import java.io.Serializable;
import py.com.generica.utils.criteria.Condition;
import py.com.generica.utils.criteria.Join;
import py.com.generica.utils.criteria.JoinKind;
import py.com.generica.utils.criteria.Projection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JoinBuilder implements Serializable {

    private Projection projection;
    private Condition on;
    private JoinKind kind;

    JoinBuilder() {
        this.projection = null;
        this.on = null;
        this.kind = JoinKind.JOIN;
    }

    public JoinBuilder kind(JoinKind kind) {
        this.kind = kind;
        return this;
    }

    public Join build() {
        return new Join(kind, projection, on);
    }

}
