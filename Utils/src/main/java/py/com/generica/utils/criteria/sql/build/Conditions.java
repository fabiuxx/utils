/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.build;

import py.com.generica.utils.criteria.Condition;
import py.com.generica.utils.criteria.Operator;
import py.com.generica.utils.criteria.column.Column;
import py.com.generica.utils.misc.text.Joiner;
import py.com.generica.utils.misc.text.Text;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Conditions {

    public static final Condition TRUE = new Condition("1", Operator.EQUALS, "1");

    public static final Condition FALSE = new Condition("1", Operator.EQUALS, "0");

    public static <T> Condition in(Column<T> column, T[] elements) {
        return in(column.getName(), elements, false);
    }

    public static <T> Condition in(String lexpression, T[] elements) {
        return in(lexpression, elements, false);
    }

    public static <T> Condition in(String lexpression, T[] elements, boolean quoted) {
        Condition condition;

        String value;
        if (elements == null || elements.length == 0) {
            condition = Conditions.TRUE;
        } else if (elements.length == 1) {
            value = String.valueOf(elements[0]);
            if (quoted) {
                value = Text.quote(value);
            }
            condition = new Condition(lexpression, Operator.EQUALS, value);
        } else {
            Joiner joiner = Joiner.of(elements);
            if (quoted) {
                joiner.quoted();
            }
            value = joiner.join();
            condition = new Condition(lexpression, Operator.IN, String.format("(%s)", value));
        }

        return condition;
    }

    public static Condition all(Condition... conditions) {
        Condition c0 = conditions[0];
        for (int i = 1; i < conditions.length; i++) {
            c0 = new Condition(c0.toString(), Operator.AND, conditions[i]);
        }
        return c0;
    }

    public static Condition any(Condition... conditions) {
        Condition c0 = conditions[0];
        for (int i = 1; i < conditions.length; i++) {
            c0 = new Condition(c0.toString(), Operator.OR, conditions[i]);
        }
        return c0;
    }

}
