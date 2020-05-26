/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.commands;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.Join;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Order;
import fa.gs.utils.database.query.elements.Projection;
import fa.gs.utils.database.query.elements.Table;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.StringBuilder2;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractQuery implements QueryPart {

    protected <T extends QueryPart> String[] stringify(Collection<T> parts, Dialect dialect) {
        if (Assertions.isNullOrEmpty(parts)) {
            return null;
        } else {
            return parts.stream()
                    .filter(e -> e != null)
                    .map(e -> e.stringify(dialect))
                    .filter(s -> !Assertions.stringNullOrEmpty(s))
                    .toArray(String[]::new);
        }
    }

    protected void withProjections(StringBuilder2 builder, Collection<Projection> projections, Dialect dialect) {
        // Projections.
        String[] projections0 = stringify(projections, dialect);
        if (!Assertions.isNullOrEmpty(projections0)) {
            builder.append(Joiner.of(projections0).separator(", ").join());
        }
    }

    protected void withFrom(StringBuilder2 builder, Table from, Dialect dialect) {
        // From.
        if (!Assertions.isNull(from)) {
            builder.append(" FROM ");
            builder.append(from.stringify(dialect));
        }
    }

    protected void withJoins(StringBuilder2 builder, Collection<Join> joins, Dialect dialect) {
        // Joins.
        String[] joins0 = stringify(joins, dialect);
        if (!Assertions.isNullOrEmpty(joins0)) {
            builder.append(" ");
            builder.append(Joiner.of(joins0).separator(" ").join());
        }
    }

    protected void withWhere(StringBuilder2 builder, Collection<Expression> where, Dialect dialect) {
        // Where.
        String[] where0 = stringify(where, dialect);
        if (!Assertions.isNullOrEmpty(where0)) {
            builder.append(" WHERE ");
            builder.append(Joiner.of(where0).separator(" and ").join());
        }
    }

    protected void withGroupBy(StringBuilder2 builder, Collection<Name> groupBy, Dialect dialect) {
        // Group by.
        String[] groupBy0 = stringify(groupBy, dialect);
        if (!Assertions.isNullOrEmpty(groupBy0)) {
            builder.append(" GROUP BY ");
            builder.append(Joiner.of(groupBy0).separator(", ").join());
        }
    }

    protected void withHaving(StringBuilder2 builder, Collection<Expression> having, Dialect dialect) {
        // Having.
        String[] having0 = stringify(having, dialect);
        if (!Assertions.isNullOrEmpty(having0)) {
            builder.append(" HAVING ");
            builder.append(Joiner.of(having0).separator(" and ").join());
        }
    }

    protected void withOrderBy(StringBuilder2 builder, Collection<Order> orderBy, Dialect dialect) {
        // Order by.
        String[] orderBy0 = stringify(orderBy, dialect);
        if (!Assertions.isNullOrEmpty(orderBy0)) {
            builder.append(" ORDER BY ");
            builder.append(Joiner.of(orderBy0).separator(", ").join());
        }
    }

    protected void withLimit(StringBuilder2 builder, Long limit) {
        // Limit.
        if (limit != null) {
            builder.append(" LIMIT ");
            builder.append(limit);
        }
    }

    protected void withOffset(StringBuilder2 builder, Long offset) {
        // Offset.
        if (offset != null) {
            builder.append(" OFFSET ");
            builder.append(offset);
        }
    }

}
