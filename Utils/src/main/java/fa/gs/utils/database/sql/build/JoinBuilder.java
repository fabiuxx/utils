/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.build;

import fa.gs.utils.database.criteria.Condition;
import fa.gs.utils.database.criteria.Join;
import fa.gs.utils.database.criteria.JoinKind;
import fa.gs.utils.database.criteria.Projection;
import java.io.Serializable;

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
