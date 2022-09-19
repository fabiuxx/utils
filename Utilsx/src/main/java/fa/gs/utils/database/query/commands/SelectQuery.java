/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.commands;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.database.query.elements.CTE;
import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.Join;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Order;
import fa.gs.utils.database.query.elements.Projection;
import fa.gs.utils.database.query.elements.Table;
import fa.gs.utils.misc.text.StringBuilder2;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SelectQuery extends AbstractQuery {

    final Collection<CTE> ctes;
    Table from;
    final Collection<Projection> projections;
    final Collection<Join> joins;
    final Collection<Expression> where;
    final Collection<Name> groupBy;
    final Collection<Expression> having;
    final Collection<Order> orderBy;
    Long limit;
    Long offset;

    /**
     * Constructor.
     */
    public SelectQuery() {
        this.ctes = new ArrayList<>();
        this.from = null;
        this.projections = new ArrayList<>();
        this.joins = new ArrayList<>();
        this.where = new ArrayList<>();
        this.groupBy = new ArrayList<>();
        this.having = new ArrayList<>();
        this.orderBy = new ArrayList<>();
        this.limit = null;
        this.offset = null;
    }

    /**
     * Convierte la sentencia de seleccion actual en una sentencia de conteo.
     *
     * @return Setencia de conteo basada en sentencia actual.
     */
    public CountQuery asCountQuery() {
        CountQuery count = new CountQuery();
        count.parameters.putAll(this.parameters);
        count.ctes.addAll(this.ctes);
        count.from = this.from;
        count.joins.addAll(this.joins);
        count.where.addAll(this.where);
        count.groupBy.addAll(this.groupBy);
        count.having.addAll(this.having);
        return count;
    }

    @Override
    public String stringify(final Dialect dialect) {
        // Generar query inicial.
        StringBuilder2 builder = new StringBuilder2();
        withCtes(builder, ctes, dialect);
        builder.append(" SELECT ");
        withProjections(builder, projections, dialect);
        withFrom(builder, from, dialect);
        withJoins(builder, joins, dialect);
        withWhere(builder, where, dialect);
        withGroupBy(builder, groupBy, dialect);
        withHaving(builder, having, dialect);
        withOrderBy(builder, orderBy, dialect);
        withLimit(builder, limit);
        withOffset(builder, offset);
        String query = builder.toString();

        // Rellenar parametros.
        query = fillParameters(query, dialect);

        return query;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public SelectQuery param(String name, QueryPart value) {
        withParameter(name, value);
        return this;
    }

    public SelectQuery cte(CTE cte) {
        Lists.add(ctes, cte);
        return this;
    }

    public SelectQuery from(Table from) {
        if (from != null) {
            this.from = from;
        }
        return this;
    }

    public SelectQuery projection(Projection projection) {
        Lists.add(projections, projection);
        return this;
    }

    public SelectQuery projections(Collection<Projection> projections) {
        Lists.add(this.projections, projections);
        return this;
    }

    public Projection[] projections() {
        return Arrays.unwrap(projections, Projection.class);
    }

    public SelectQuery join(Join join) {
        Lists.add(joins, join);
        return this;
    }

    public Join[] joins() {
        return Arrays.unwrap(joins, Join.class);
    }

    public SelectQuery where(Expression expression) {
        Lists.add(where, expression);
        return this;
    }

    public Expression[] where() {
        return Arrays.unwrap(where, Expression.class);
    }

    public SelectQuery groupBy(Name group) {
        Lists.add(groupBy, group);
        return this;
    }

    public Name[] groupsBy() {
        return Arrays.unwrap(groupBy, Name.class);
    }

    public SelectQuery having(Expression expression) {
        Lists.add(having, expression);
        return this;
    }

    public Expression[] having() {
        return Arrays.unwrap(having, Expression.class);
    }

    public SelectQuery orderBy(Order group) {
        if (group != null) {
            this.orderBy.add(group);
        }
        return this;
    }

    public Order[] ordersBy() {
        return Arrays.unwrap(orderBy, Order.class);
    }

    public SelectQuery limit(Long limit) {
        this.limit = limit;
        return this;
    }

    public Long limit() {
        return limit;
    }

    public SelectQuery offset(Long offset) {
        this.offset = offset;
        return this;
    }

    public Long offset() {
        return offset;
    }
    //</editor-fold>

}
