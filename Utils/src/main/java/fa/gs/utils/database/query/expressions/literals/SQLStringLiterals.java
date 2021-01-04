/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.literals;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.misc.text.Text;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SQLStringLiterals {

    public static final String WHITESPACE_SEPARATOR = " ";

    public static String likefy(String value) {
        return contains(value, null);
    }

    public static String phrasify(String value) {
        return contains(value, WHITESPACE_SEPARATOR);
    }

    public static String contains(String value, String separator) {
        if (Assertions.stringNullOrEmpty(value)) {
            return "";
        }

        if (Assertions.stringNullOrEmpty(separator)) {
            return Strings.format("%%%s%%", value);
        } else {
            String[] parts = value.split(separator);
            return Joiner.of(parts).prefix("%").posfix("%").separator(" ").join();
        }
    }

    public static String startsWith(String value) {
        return Strings.format("%s%%", value);
    }

    public static String endsWith(String value) {
        return Strings.format("%%%s", value);
    }

    public static String fecha(Date fecha) {
        return fecha(fecha, true);
    }

    public static String hora(Date fecha) {
        return hora(fecha, true);
    }

    public static String fechaHora(Date fecha) {
        return fechaHora(fecha, true);
    }

    public static String fecha(Date fecha, boolean quoted) {
        String txt = Fechas.toString(fecha, "yyyy-MM-dd");
        if (quoted) {
            return Text.safeQuoteSingle(txt);
        } else {
            return txt;
        }
    }

    public static String hora(Date fecha, boolean quoted) {
        String txt = Fechas.toString(fecha, "HH:mm:ss");
        if (quoted) {
            return Text.safeQuoteSingle(txt);
        } else {
            return txt;
        }
    }

    public static String fechaHora(Date fecha, boolean quoted) {
        String txt = Fechas.toString(fecha, "yyyy-MM-dd HH:mm:ss");
        if (quoted) {
            return Text.safeQuoteSingle(txt);
        } else {
            return txt;
        }
    }

}
