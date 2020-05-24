/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.elements.Expression;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Expressions {

    public static Expression build(final Dialect dialect, final String raw) {
        return new Expression() {
            @Override
            public String stringify(Dialect dialect) {
                return raw;
            }
        };
    }

}
