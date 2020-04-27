/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.misc.text.Text;
import java.util.Date;

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

    public static String fecha(Date fecha) {
        String txt = Fechas.toString(fecha, "yyyy-MM-dd");
        return Text.safeQuoteSingle(txt);
    }

    public static String hora(Date fecha) {
        String txt = Fechas.toString(fecha, "HH:mm:ss");
        return Text.safeQuoteSingle(txt);
    }

    public static String fechaHora(Date fecha) {
        String txt = Fechas.toString(fecha, "yyyy-MM-dd HH:mm:ss");
        return Text.safeQuoteSingle(txt);
    }

}
