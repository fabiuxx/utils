/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.build;

import fa.gs.utils.database.criteria.Condition;
import fa.gs.utils.database.criteria.Operator;
import fa.gs.utils.database.criteria.column.Column;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.Text;

/**
 *
 * @author Fabio A. González Sosa
 */
public final class NativeQueryConditions {

    public <T> Condition in(Column<T> column, T[] elements) {
        return in(column.getName(), elements, false);
    }

    public <T> Condition in(String lexpression, T[] elements) {
        return in(lexpression, elements, false);
    }

    public <T> Condition in(String lexpression, T[] elements, boolean quoted) {
        Condition condition;

        String value;
        if (elements == null || elements.length == 0) {
            condition = Conditions.TRUE;
        } else if (elements.length == 1) {
            value = String.valueOf(elements[0]);
            if (quoted) {
                value = Text.quoteSingle(value);
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

}
