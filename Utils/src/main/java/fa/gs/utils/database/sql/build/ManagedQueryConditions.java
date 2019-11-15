/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.build;

import fa.gs.utils.database.criteria.Condition;
import fa.gs.utils.database.criteria.Operator;
import fa.gs.utils.database.criteria.column.Column;

/**
 *
 * @author Fabio A. González Sosa
 */
public final class ManagedQueryConditions {

    public <T> Condition in(Column<T> column, T[] elements) {
        return Builders.condition().left(column)
                .op(Operator.IN)
                .right(elements)
                .build();
    }

    public <T> Condition notIn(Column<T> column, T[] elements) {
        return Builders.condition().left(column)
                .op(Operator.NOT_IN)
                .right(elements)
                .build();
    }

}
