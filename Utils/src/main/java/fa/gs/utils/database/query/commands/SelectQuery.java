/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.commands;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
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

    Table from;
    final Collection<Projection> projections;
    final Collection<Join> joins;
    final Collection<Expression> where;
    final Collection<Name> groupBy;
    final Collection<Expression> having;
    final Collection<Order> orderBy;
    Long limit;
    Long offset;

    public SelectQuery() {
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

    public CountQuery asCountQuery() {
        CountQuery count = new CountQuery();
        count.from = this.from;
        count.joins.addAll(this.joins);
        count.where.addAll(this.where);
        count.groupBy.addAll(this.groupBy);
        count.having.addAll(this.having);
        return count;
    }

    @Override
    public String stringify(final Dialect dialect) {
        StringBuilder2 builder = new StringBuilder2();
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
        return builder.toString();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
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
