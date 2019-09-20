/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria.sql.format;

import fa.gs.utils.criteria.Condition;
import fa.gs.utils.criteria.Grouping;
import fa.gs.utils.criteria.QueryKind;
import fa.gs.utils.criteria.SelectCriteria;
import fa.gs.utils.criteria.Sorting;
import fa.gs.utils.criteria.sql.build.Conditions;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.StringBuilder2;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SelectCriteriaFormatter {

    public static <T extends SelectCriteria> String toString(T criteria) {
        StringBuilder2 builder = new StringBuilder2();
        builder.append("( SELECT");
        appendProjections(builder, criteria);
        builder.append(" FROM %s ", ProjectionFormatter.toString(criteria.getFrom()));
        appendJoins(builder, criteria);
        appendFilters(builder, criteria);
        appendGroupings(builder, criteria);
        appendSortings(builder, criteria);
        appendLimits(builder, criteria);
        builder.append(")");
        return builder.toString().trim();
    }

    private static void appendProjections(StringBuilder2 builder, SelectCriteria criteria) {
        QueryKind kind = criteria.getKind();

        if (kind == QueryKind.COUNT) {
            builder.append(" count(*) as %s ", SqlLiterals.TOTAL_COLUMN_NAME);
            return;
        }

        if (kind == QueryKind.SELECT) {
            if (Assertions.isNullOrEmpty(criteria.getProjections())) {
                builder.append(" * ");
            } else {
                builder.append(" ");
                builder.append(ProjectionFormatter.toString(criteria.getProjections()));
            }
        }
    }

    private static void appendJoins(StringBuilder2 builder, SelectCriteria criteria) {
        builder.append(" ");
        builder.append(JoinFormatter.toString(criteria.getJoins()));
    }

    private static void appendFilters(StringBuilder2 builder, SelectCriteria criteria) {
        Collection<Condition> filters = criteria.getFilters();
        if (!Assertions.isNullOrEmpty(filters)) {
            builder.append(" WHERE ");
            builder.append(ConditionFormatter.toString(Conditions.TRUE));
            builder.append(" and ( ");
            builder.append(ConditionFormatter.toString(filters));
            builder.append(" ) ");
        }
    }

    private static void appendSortings(StringBuilder2 builder, SelectCriteria criteria) {
        if (criteria.getKind() == QueryKind.SELECT) {
            Collection<Sorting> sortings = criteria.getSorts();
            if (!Assertions.isNullOrEmpty(sortings)) {
                builder.append(" ORDER BY ");
                builder.append(SortingFormatter.toString(sortings));
            }
        }
    }

    private static void appendGroupings(StringBuilder2 builder, SelectCriteria criteria) {
        if (criteria.getKind() == QueryKind.SELECT) {
            Collection<Grouping> groupings = criteria.getGroupings();
            if (!Assertions.isNullOrEmpty(groupings)) {
                builder.append(" GROUP BY ");
                builder.append(GroupingFormatter.toString(groupings));
            }
        }
    }

    private static void appendLimits(StringBuilder2 builder, SelectCriteria criteria) {
        if (criteria.getKind() == QueryKind.SELECT) {
            if (criteria.getLimit() != null) {
                builder.append(" LIMIT %s", criteria.getLimit());
            }

            if (criteria.getOffset() != null) {
                builder.append(" OFFSET %s", criteria.getOffset());
            }
        }
    }

}
