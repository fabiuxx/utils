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
public class CountQuery extends AbstractQuery {

    public static final String COUNT_FIELD_NAME = "total_count";

    Table from;
    Join[] joins;
    Expression where;
    Name[] groupBy;
    Expression having;

    public CountQuery() {
        this.from = null;
        this.joins = null;
        this.where = null;
        this.groupBy = null;
        this.having = null;
    }

    @Override
    public String stringify(Dialect dialect) {
        StringBuilder2 builder = new StringBuilder2();

        // Projections.
        builder.append(" SELECT COUNT(*) AS \"%s\"", CountQuery.COUNT_FIELD_NAME);

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

        return builder.toString();
    }

}
