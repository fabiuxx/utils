/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.build;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Order;
import fa.gs.utils.mixins.Self;

/**
 *
 * @author Fabio A. González Sosa
 */
public class OrderBuilder<T extends OrderBuilder<T>> implements Self<T> {

    private Name value;
    private Order.Type type;

    OrderBuilder() {
        this.value = null;
        this.type = Order.Type.ASC;
    }

    public static OrderBuilder instance() {
        return new OrderBuilder();
    }

    public T column(String... parts) {
        this.value = new Name(parts);
        return self();
    }

    public T column(Name name) {
        this.value = name;
        return self();
    }

    public OrderBuilder type(Order.Type type) {
        this.type = type;
        return self();
    }

    public OrderBuilder asc() {
        return type(Order.Type.ASC);
    }

    public OrderBuilder desc() {
        return type(Order.Type.DESC);
    }

    public Order build(Dialect dialect) {
        Name value0 = value;
        Order.Type type0 = type;
        return new Order(value0, type0);
    }

}
