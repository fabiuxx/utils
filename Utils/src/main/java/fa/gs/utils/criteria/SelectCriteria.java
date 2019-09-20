/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria;

import fa.gs.utils.collections.Lists;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SelectCriteria extends QueryCriteria<SelectCriteria> {

    protected QueryKind kind;
    protected Projection from;
    protected final Collection<Projection> projections;
    protected final Collection<Join> joins;
    protected final Collection<Grouping> groupings;

    private SelectCriteria(QueryKind kind) {
        this.kind = kind;
        this.from = null;
        this.projections = Lists.empty();
        this.joins = Lists.empty();
        this.groupings = Lists.empty();
    }

    public static SelectCriteria count() {
        return new SelectCriteria(QueryKind.COUNT);
    }

    public static SelectCriteria all() {
        return new SelectCriteria(QueryKind.SELECT);
    }

    public static SelectCriteria select() {
        return new SelectCriteria(QueryKind.SELECT);
    }

    public SelectCriteria from(Projection value) {
        this.from = value;
        return self();
    }

    public SelectCriteria projection(Projection value) {
        this.projections.add(value);
        return self();
    }

    public SelectCriteria projection(Collection<Projection> values) {
        this.projections.addAll(values);
        return self();
    }

    public SelectCriteria join(Join value) {
        this.joins.add(value);
        return self();
    }

    public SelectCriteria join(Collection<Join> value) {
        this.joins.addAll(value);
        return self();
    }

    public SelectCriteria group(Grouping grouping) {
        this.groupings.add(grouping);
        return self();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public QueryKind getKind() {
        return kind;
    }

    public void setKind(QueryKind kind) {
        this.kind = kind;
    }

    public Projection getFrom() {
        return from;
    }

    public void setFrom(Projection from) {
        this.from = from;
    }

    public Collection<Projection> getProjections() {
        return projections;
    }

    public Collection<Join> getJoins() {
        return joins;
    }

    public Collection<Grouping> getGroupings() {
        return groupings;
    }
    //</editor-fold>

}
