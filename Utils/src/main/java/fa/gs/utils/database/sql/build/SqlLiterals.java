/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.build;

import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.text.Text;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SqlLiterals {

    public static final String TOTAL_COLUMN_NAME = "total";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String TRUE_CHAR = "1";

    public static final String FALSE_CHAR = "0";

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
        return Text.quoteSingle(txt);
    }

    public static String string(String text) {
        return Text.quoteSingle(text);
    }

    public static String like(String text) {
        String like = String.format("%%%s%%", text);
        return Text.quoteSingle(like);
    }

}
