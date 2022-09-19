/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.misc.errors.Errors;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Names {

    public static Name build(Object value) {
        // Nombre.
        if (value instanceof String) {
            return new Name((String) value);
        } else {
            throw Errors.illegalArgument("Expresion de tabla no soportada.");
        }
    }

}
