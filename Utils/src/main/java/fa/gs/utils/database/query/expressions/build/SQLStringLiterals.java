/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SQLStringLiterals {

    public static String contains(String value) {
        return Strings.format("%%%s%%", value);
    }

    public static String startsWith(String value) {
        return Strings.format("%s%%", value);
    }

    public static String endsWith(String value) {
        return Strings.format("%%%s", value);
    }

}
