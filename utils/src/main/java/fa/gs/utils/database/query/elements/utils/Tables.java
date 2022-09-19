/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Table;
import fa.gs.utils.database.query.elements.build.TableBuilder;
import fa.gs.utils.misc.errors.Errors;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Tables {

    public static Table build(Object projection, String alias) {
        TableBuilder builder = TableBuilder.instance();

        // Proyeccion.
        if (projection instanceof String) {
            builder.table().wrap((String) projection);
        } else if (projection instanceof Name) {
            builder.table().name((Name) projection);
        } else {
            throw Errors.illegalArgument("Expresion de tabla no soportada.");
        }

        // Alias.
        builder.as(alias);

        return builder.build();
    }

}
