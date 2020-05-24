/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.build;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Projection;
import fa.gs.utils.mixins.Self;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class ProjectionBuilder<T extends ProjectionBuilder<T>> implements Self<T> {

    private final ExpressionBuilder value;
    private Name alias;

    ProjectionBuilder() {
        this.value = ExpressionBuilder.instance();
        this.alias = null;
    }

    public static ProjectionBuilder instance() {
        return new ProjectionBuilder();
    }

    public ExpressionBuilder projection() {
        return value;
    }

    public T as(String... parts) {
        this.alias = new Name(parts);
        return self();
    }

    public T as(Name name) {
        this.alias = name;
        return self();
    }

    public Projection build(Dialect dialect) {
        Expression value0 = value.build(dialect);
        Name alias0 = alias;
        return new Projection(value0, alias0);
    }

}
