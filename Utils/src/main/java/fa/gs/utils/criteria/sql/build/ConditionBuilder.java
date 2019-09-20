/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria.sql.build;

import fa.gs.utils.criteria.Condition;
import fa.gs.utils.criteria.Operator;
import fa.gs.utils.criteria.column.Column;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ConditionBuilder implements Serializable {

    private String left;
    private Operator operator;
    private Object rigth;

    ConditionBuilder() {
        this.left = "";
        this.operator = null;
        this.rigth = "";
    }

    public ConditionBuilder left(String value) {
        this.left = value;
        return this;
    }

    public ConditionBuilder left(Column<?> value) {
        left(value.getName());
        return this;
    }

    public ConditionBuilder op(Operator value) {
        this.operator = value;
        return this;
    }

    public ConditionBuilder right(Object value) {
        this.rigth = value;
        return this;
    }

    public Condition build() {
        return new Condition(left, operator, rigth);
    }

}
