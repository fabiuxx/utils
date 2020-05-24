/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Projection;
import fa.gs.utils.database.query.elements.build.ProjectionBuilder;
import fa.gs.utils.misc.errors.Errors;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Projections {

    public static Projection build(Dialect dialect, Object projection, String alias) {
        ProjectionBuilder builder = ProjectionBuilder.instance();

        // Proyeccion.
        if (projection instanceof String) {
            builder.projection().wrap((String) projection);
        } else if (projection instanceof Name) {
            builder.projection().name((Name) projection);
        } else {
            throw Errors.illegalArgument("Proyeccion no soportada.");
        }

        // Alias.
        builder.as(alias);

        return builder.build(dialect);
    }

}
