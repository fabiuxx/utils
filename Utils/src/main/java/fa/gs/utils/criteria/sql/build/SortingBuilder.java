/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria.sql.build;

import fa.gs.utils.criteria.OrderKind;
import fa.gs.utils.criteria.Sorting;
import fa.gs.utils.criteria.column.Column;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SortingBuilder implements Serializable {

    private String name;
    private OrderKind order;

    SortingBuilder() {
        this.name = "";
        this.order = OrderKind.ASCENDING;
    }

    public SortingBuilder name(String value) {
        this.name = value;
        return this;
    }

    public SortingBuilder name(Column<?> value) {
        return name(value.getName());
    }

    public SortingBuilder order(OrderKind kind) {
        this.order = kind;
        return this;
    }

    public Sorting build() {
        return new Sorting(name, order);
    }

}
