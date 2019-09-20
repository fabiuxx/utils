/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.build;

import java.io.Serializable;
import py.com.generica.utils.criteria.OrderKind;
import py.com.generica.utils.criteria.Sorting;
import py.com.generica.utils.criteria.column.Column;

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
