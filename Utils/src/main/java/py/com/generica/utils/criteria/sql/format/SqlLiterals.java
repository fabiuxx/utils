/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.format;

import java.util.Date;
import py.com.generica.utils.misc.fechas.Fechas;
import py.com.generica.utils.misc.text.Text;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SqlLiterals {

    public static final String TOTAL_COLUMN_NAME = "total";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String fecha(Date fecha) {
        return formatFecha(fecha, DATE_FORMAT);
    }

    public static String hora(Date fecha) {
        return formatFecha(fecha, TIME_FORMAT);
    }

    public static String fechaHora(Date fecha) {
        return formatFecha(fecha, DATETIME_FORMAT);
    }

    private static String formatFecha(Date fecha, String format) {
        String txt = Fechas.toString(fecha, format);
        return Text.quote(txt);
    }

    public static String string(String text) {
        return Text.quote(text);
    }

    public static String like(String text) {
        String like = String.format("%%%s%%", text);
        return Text.quote(like);
    }

}
