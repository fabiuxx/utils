/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.build;

import fa.gs.utils.database.criteria.Projection;
import fa.gs.utils.database.criteria.column.Column;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ProjectionBuilder implements Serializable {

    private String name;
    private String as;

    ProjectionBuilder() {
        this.name = null;
        this.as = null;
    }

    public ProjectionBuilder name(String value) {
        this.name = value;
        return this;
    }

    public ProjectionBuilder name(Column<?> value) {
        return name(value.getName());
    }

    public ProjectionBuilder as(String value) {
        this.as = value;
        return this;
    }

    public ProjectionBuilder as(Column<?> value) {
        return as(value.getName());
    }

    public Projection build() {
        return new Projection(name, as);
    }

}
