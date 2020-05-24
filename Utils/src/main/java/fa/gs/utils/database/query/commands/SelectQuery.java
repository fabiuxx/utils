/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.commands;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.Join;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Order;
import fa.gs.utils.database.query.elements.Projection;
import fa.gs.utils.database.query.elements.Table;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.StringBuilder2;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class SelectQuery extends AbstractQuery {

    Table from;
    Projection[] projections;
    Join[] joins;
    Expression where;
    Name[] groupBy;
    Expression having;
    Order[] orderBy;
    Long limit;
    Long offset;

    public SelectQuery() {
        this.from = null;
        this.projections = null;
        this.joins = null;
        this.where = null;
        this.groupBy = null;
        this.having = null;
        this.orderBy = null;
        this.limit = null;
        this.offset = null;
    }

    public CountQuery forCount() {
        CountQuery count = new CountQuery();
        count.from = this.from;
        count.joins = this.joins;
        count.where = this.where;
        count.groupBy = this.groupBy;
        count.having = this.having;
        return count;
    }

    @Override
    public String stringify(final Dialect dialect) {
        StringBuilder2 builder = new StringBuilder2();

        // Projections.
        String[] projections0 = stringify(projections, dialect);
        if (!Assertions.isNullOrEmpty(projections0)) {
            builder.append(" SELECT ");
            builder.append(Joiner.of(projections0).separator(", ").join());
        }

        // From.
        if (!Assertions.isNull(from)) {
            builder.append(" FROM ");
            builder.append(from.stringify(dialect));
        }

        // Joins.
        if (!Assertions.isNullOrEmpty(joins)) {
            String[] joins0 = stringify(joins, dialect);
            if (!Assertions.isNullOrEmpty(joins0)) {
                builder.append(" ");
                builder.append(Joiner.of(joins0).separator(" ").join());
            }
        }

        // Where.
        if (!Assertions.isNull(where)) {
            builder.append(" WHERE ");
            builder.append(where.stringify(dialect));
        }

        // Group by.
        if (!Assertions.isNullOrEmpty(groupBy)) {
            String[] groupBy0 = stringify(groupBy, dialect);
            if (!Assertions.isNullOrEmpty(groupBy0)) {
                builder.append(" GROUP BY ");
                builder.append(Joiner.of(groupBy0).separator(", ").join());
            }
        }

        // Having.
        if (!Assertions.isNull(having)) {
            builder.append(" HAVING ");
            builder.append(having.stringify(dialect));
        }

        // Order by.
        if (!Assertions.isNullOrEmpty(orderBy)) {
            String[] orderBy0 = stringify(orderBy, dialect);
            if (!Assertions.isNullOrEmpty(orderBy0)) {
                builder.append(" ORDER BY ");
                builder.append(Joiner.of(orderBy0).separator(", ").join());
            }
        }

        // Limit.
        if (limit != null) {
            builder.append(" LIMIT ");
            builder.append(limit);
        }

        // Offset.
        if (offset != null) {
            builder.append(" OFFSET ");
            builder.append(offset);
        }

        return builder.toString();
    }

}
