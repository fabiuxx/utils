/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Order;
import fa.gs.utils.database.query.elements.build.OrderBuilder;
import fa.gs.utils.misc.errors.Errors;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Orders {

    public static Order build(Object name) {
        return build(name, Order.Type.ASC);
    }

    public static Order build(Object name, Order.Type type) {
        OrderBuilder builder = OrderBuilder.instance();

        // Columna.
        if (name instanceof String) {
            String name0 = (String) name;
            builder.column(name0);
        } else if (name instanceof QueryPart) {
            QueryPart name0 = (QueryPart) name;
            builder.column(name0);
        } else {
            throw Errors.illegalArgument("Columna no soportada.");
        }

        // Tipo de ordenacion.
        builder.type(type);

        return builder.build();
    }

}
