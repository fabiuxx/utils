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
import fa.gs.utils.database.query.elements.Table;
import fa.gs.utils.misc.text.StringBuilder2;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class CountQuery extends AbstractQuery {

    public static final String COUNT_FIELD_NAME = "total_count";

    Table from;
    final Collection<Join> joins;
    final Collection<Expression> where;
    final Collection<Name> groupBy;
    final Collection<Expression> having;

    /**
     * Constructor.
     */
    public CountQuery() {
        this.from = null;
        this.joins = new ArrayList<>();
        this.where = new ArrayList<>();
        this.groupBy = new ArrayList<>();
        this.having = new ArrayList<>();
    }

    @Override
    public String stringify(Dialect dialect) {
        StringBuilder2 builder = new StringBuilder2();
        builder.append(" SELECT COUNT(*) AS \"%s\"", CountQuery.COUNT_FIELD_NAME);
        withFrom(builder, from, dialect);
        withJoins(builder, joins, dialect);
        withWhere(builder, where, dialect);
        withGroupBy(builder, groupBy, dialect);
        withHaving(builder, having, dialect);
        return builder.toString();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public CountQuery from(Table from) {
        if (from != null) {
            this.from = from;
        }
        return this;
    }

    public CountQuery join(Join join) {
        Lists.add(joins, join);
        return this;
    }

    public Join[] joins() {
        return Arrays.unwrap(joins, Join.class);
    }

    public CountQuery where(Expression expression) {
        Lists.add(where, expression);
        return this;
    }

    public Expression[] where() {
        return Arrays.unwrap(where, Expression.class);
    }

    public CountQuery groupBy(Name group) {
        Lists.add(groupBy, group);
        return this;
    }

    public Name[] groupsBy() {
        return Arrays.unwrap(groupBy, Name.class);
    }

    public CountQuery having(Expression expression) {
        Lists.add(having, expression);
        return this;
    }

    public Expression[] having() {
        return Arrays.unwrap(having, Expression.class);
    }
    //</editor-fold>

}
