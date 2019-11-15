/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.build;

import fa.gs.utils.database.criteria.Condition;
import fa.gs.utils.database.criteria.Operator;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Conditions {

    public static final Condition TRUE = new Condition("1", Operator.EQUALS, "1");

    public static final Condition FALSE = new Condition("1", Operator.EQUALS, "0");

    private static final NativeQueryConditions nativeConditions = new NativeQueryConditions();

    private static final ManagedQueryConditions managedConditions = new ManagedQueryConditions();

    public static NativeQueryConditions natives() {
        return nativeConditions;
    }

    public static ManagedQueryConditions managed() {
        return managedConditions;
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
