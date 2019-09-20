/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.build;

import java.util.Collection;
import py.com.generica.utils.criteria.Condition;
import py.com.generica.utils.criteria.Grouping;
import py.com.generica.utils.criteria.Join;
import py.com.generica.utils.criteria.JoinKind;
import py.com.generica.utils.criteria.Operator;
import py.com.generica.utils.criteria.OrderKind;
import py.com.generica.utils.criteria.Pagination;
import py.com.generica.utils.criteria.Projection;
import py.com.generica.utils.criteria.QueryCriteria;
import py.com.generica.utils.criteria.SelectCriteria;
import py.com.generica.utils.criteria.Sorting;
import py.com.generica.utils.criteria.column.Column;
import py.com.generica.utils.criteria.sql.format.SelectCriteriaFormatter;
import py.com.generica.utils.misc.Assertions;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Criterias {

    public static <T extends SelectCriteria> String toQuery(T criteria) {
        return SelectCriteriaFormatter.toString(criteria);
    }

    public static <T extends SelectCriteria, S extends QueryCriteria> T wrap(T selectCriteria, S criteria) {
        if (criteria != null) {
            where(selectCriteria, criteria.getFilters());
            order(selectCriteria, criteria.getSorts());
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

    public static <T extends QueryCriteria> T where(T criteria, Condition condition) {
        if (condition != null) {
            criteria.where(condition);
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

    public static <T extends QueryCriteria> T order(T criteria, Sorting sorting) {
        if (sorting != null) {
            criteria.order(sorting);
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

    public static <T extends SelectCriteria> T select(T criteria, String name) {
        return select(criteria, name, "");
    }

    public static <T extends SelectCriteria> T select(T criteria, String name, Column<?> as) {
        return select(criteria, name, as.getName());
    }

    public static <T extends SelectCriteria> T select(T criteria, String name, String as) {
        Projection p = new Projection(name, as);
        criteria.projection(p);
        return criteria;
    }

    public static <T extends SelectCriteria> T from(T criteria, String name) {
        return from(criteria, name, "");
    }

    public static <T extends SelectCriteria> T from(T criteria, String name, String as) {
        Projection p = new Projection(name, as);
        criteria.from(p);
        return criteria;
    }

    public static <T extends SelectCriteria> T join(T criteria, String projection, String as, String lexp, Operator operator, Object rexp) {
        return join(criteria, JoinKind.JOIN, projection, as, lexp, operator, rexp);
    }

    public static <T extends SelectCriteria> T join(T criteria, JoinKind joinKind, String projection, String as, String lexp, Operator operator, Object rexp) {
        Projection p = new Projection(projection, as);
        Condition c = new Condition(lexp, operator, rexp);
        Join j = new Join(joinKind, p, c);
        criteria.join(j);
        return criteria;
    }

    public static <T extends SelectCriteria, S extends QueryCriteria> T limit(T selectCriteria, S criteria) {
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

    public static <T extends SelectCriteria> T group(T criteria, Column<?> column) {
        return group(criteria, column.getName());
    }

    public static <T extends SelectCriteria> T group(T criteria, String expression) {
        Grouping grouping = new Grouping(expression);
        criteria.group(grouping);
        return criteria;
    }

}
