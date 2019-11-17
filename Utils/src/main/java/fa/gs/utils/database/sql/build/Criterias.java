/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.build;

import fa.gs.utils.database.criteria.Condition;
import fa.gs.utils.database.criteria.Grouping;
import fa.gs.utils.database.criteria.Join;
import fa.gs.utils.database.criteria.JoinKind;
import fa.gs.utils.database.criteria.Operator;
import fa.gs.utils.database.criteria.OrderKind;
import fa.gs.utils.database.criteria.Pagination;
import fa.gs.utils.database.criteria.Projection;
import fa.gs.utils.database.criteria.QueryCriteria;
import fa.gs.utils.database.criteria.Sorting;
import fa.gs.utils.database.criteria.column.Column;
import fa.gs.utils.database.mapping.Mapping;
import fa.gs.utils.database.sql.format.SelectCriteriaFormatter;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Text;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Criterias {

    public static <T extends QueryCriteria> String toQuery(T criteria) {
        return SelectCriteriaFormatter.toString(criteria);
    }

    public static <T extends QueryCriteria, S extends QueryCriteria> T wrap(T selectCriteria, S criteria) {
        if (criteria != null) {
            where(selectCriteria, criteria.getFilters());
            order(selectCriteria, criteria.getSorts());
            group(selectCriteria, criteria.getGroupings());
            limit(selectCriteria, criteria);
        }
        return selectCriteria;
    }

    public static <T extends QueryCriteria> T where(T criteria, Collection<Condition> conditions) {
        if (!Assertions.isNullOrEmpty(conditions)) {
            for (Condition condition : conditions) {
                where(criteria, condition);
            }
        }
        return criteria;
    }

    public static <T extends QueryCriteria> T where(T criteria, Condition... conditions) {
        if (!Assertions.isNullOrEmpty(conditions)) {
            for (Condition condition : conditions) {
                if (condition != null) {
                    criteria.where(condition);
                }
            }
        }
        return criteria;
    }

    public static <T extends QueryCriteria> T where(T criteria, Column<?> column, Operator operator, Object value) {
        return where(criteria, column.getName(), operator, value);
    }

    public static <T extends QueryCriteria> T where(T criteria, String lexp, Operator operator, Object rexp) {
        Condition c = new Condition(lexp, operator, rexp);
        criteria.where(c);
        return criteria;
    }

    public static <T extends QueryCriteria> T where(T criteria, Column<?> column, Operator operator, Object[] value) {
        return where(criteria, column.getName(), operator, value);
    }

    public static <T extends QueryCriteria> T where(T criteria, String lexp, Operator operator, Object[] rexp) {
        Condition c = new Condition(lexp, operator, rexp);
        criteria.where(c);
        return criteria;
    }

    public static <T extends QueryCriteria> T order(T criteria, Collection<Sorting> sortings) {
        if (!Assertions.isNullOrEmpty(sortings)) {
            for (Sorting sorting : sortings) {
                order(criteria, sorting);
            }
        }
        return criteria;
    }

    public static <T extends QueryCriteria> T order(T criteria, Sorting... sortings) {
        if (!Assertions.isNullOrEmpty(sortings)) {
            for (Sorting sorting : sortings) {
                if (sorting != null) {
                    criteria.order(sorting);
                }
            }
        }
        return criteria;
    }

    public static <T extends QueryCriteria> T order(T criteria, Column<?> column) {
        return order(criteria, column, OrderKind.ASCENDING);
    }

    public static <T extends QueryCriteria> T order(T criteria, Column<?> column, OrderKind order) {
        return order(criteria, column.getName(), order);
    }

    public static <T extends QueryCriteria> T order(T criteria, String expression) {
        return order(criteria, expression, OrderKind.ASCENDING);
    }

    public static <T extends QueryCriteria> T order(T criteria, String expression, OrderKind order) {
        Sorting s = new Sorting(expression, order);
        criteria.order(s);
        return criteria;
    }

    public static <T extends QueryCriteria> T group(T criteria, Collection<Grouping> groupings) {
        if (!Assertions.isNullOrEmpty(groupings)) {
            for (Grouping grouping : groupings) {
                group(criteria, grouping);
            }
        }
        return criteria;
    }

    public static <T extends QueryCriteria> T group(T criteria, Grouping... groupings) {
        if (!Assertions.isNullOrEmpty(groupings)) {
            for (Grouping grouping : groupings) {
                if (grouping != null) {
                    criteria.group(grouping);
                }
            }
        }
        return criteria;
    }

    public static <T extends QueryCriteria> T group(T criteria, Column<?> column) {
        return group(criteria, column.getName());
    }

    public static <T extends QueryCriteria> T group(T criteria, String expression) {
        Grouping grouping = new Grouping(expression);
        criteria.group(grouping);
        return criteria;
    }

    public static <T extends QueryCriteria> T select(T criteria, String name) {
        return select(criteria, name, "");
    }

    /**
     * TODO: ELIMINAR.
     *
     * @param <T>
     * @param criteria
     * @param name
     * @param as
     * @return
     * @deprecated
     */
    @Deprecated
    public static <T extends QueryCriteria> T select(T criteria, String name, Column<?> as) {
        return select(criteria, name, as.getName());
    }

    public static <T extends QueryCriteria> T select(T criteria, Column<?> column, Mapping<?> as) {
        String as0 = Text.safeQuoteDouble(as.symbol().getName());
        return select(criteria, column.getName(), as0);
    }

    public static <T extends QueryCriteria> T select(T criteria, String name, String as) {
        Projection p = new Projection(name, as);
        criteria.projection(p);
        return criteria;
    }

    public static <T extends QueryCriteria> T from(T criteria, String name) {
        return from(criteria, name, "");
    }

    public static <T extends QueryCriteria> T from(T criteria, String name, String as) {
        Projection p = new Projection(name, as);
        criteria.from(p);
        return criteria;
    }

    public static <T extends QueryCriteria> T join(T criteria, String projection, String as, String lexp, Operator operator, Object rexp) {
        return join(criteria, JoinKind.JOIN, projection, as, lexp, operator, rexp);
    }

    public static <T extends QueryCriteria> T join(T criteria, JoinKind joinKind, String projection, String as, Column<?> lexp, Operator operator, Column<?> rexp) {
        return join(criteria, joinKind, projection, as, lexp.getName(), operator, rexp.getName());
    }

    public static <T extends QueryCriteria> T join(T criteria, JoinKind joinKind, String projection, String as, String lexp, Operator operator, Object rexp) {
        Projection p = new Projection(projection, as);
        Condition c = new Condition(lexp, operator, rexp);
        Join j = new Join(joinKind, p, c);
        criteria.join(j);
        return criteria;
    }

    public static <T extends QueryCriteria, S extends QueryCriteria> T limit(T selectCriteria, S criteria) {
        if (criteria != null) {
            selectCriteria.limit(criteria.getLimit());
            selectCriteria.offset(criteria.getOffset());
        }
        return selectCriteria;
    }

    public static <T extends QueryCriteria> T limit(T criteria, Integer limit) {
        return limit(criteria, limit, null);
    }

    public static <T extends QueryCriteria> T limit(T criteria, Pagination pagination) {
        if (pagination != null) {
            limit(criteria, pagination.getLimit(), pagination.getOffset());
        }
        return criteria;
    }

    public static <T extends QueryCriteria> T limit(T criteria, Integer limit, Integer offset) {
        criteria.limit(limit);
        criteria.offset(offset);
        return criteria;
    }

}
