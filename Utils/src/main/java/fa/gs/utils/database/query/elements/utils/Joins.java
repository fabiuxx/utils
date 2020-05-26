/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.elements.Join;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.build.JoinBuilder;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Joins {

    public static Join build(Join.Type type, Name table, String alias, String on) {
        JoinBuilder builder = JoinBuilder.instance();
        builder.type(type);
        builder.table().table().name(table);
        builder.table().as(alias);
        builder.on().wrap(on);
        return builder.build();
    }

}
