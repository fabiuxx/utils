/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.text;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Text {

    /**
     * Retorna el separador de linea por defecto del sistema.
     *
     * @return Separador de linea.
     */
    public static String nl() {
        return System.lineSeparator();
    }

    /**
     * Retorna una cadena por defecto a utilizar cuando un valor no esta
     * disponible para su representacion en forma de texto.
     *
     * @return Cadena por defecto para valores ausentes.
     */
    public static String nd() {
        return "N/D";
    }

    /**
     * Genera una cadena de identacion por espacios en blanco.
     *
     * @param ident Cantidad de identacion.
     * @return Cadena de identacion.
     */
    public static String ident(int ident) {
        StringBuilder builder = new StringBuilder();
        while (ident > 0) {
            builder.append(" ");
            ident--;
        }
        return builder.toString();
    }

    /**
     * Determina si una cadena de texto esta acomillada.
     *
     * @param txt Cadena de texto.
     * @return {@code true} si la cadena esta acomillada, caso contrario
     * {@code false}.
     */
    public static boolean isSingleQuoted(String txt) {
        return isQuoted(txt, '\'');
    }

    /**
     * Determina si una cadena de texto esta acomillada.
     *
     * @param txt Cadena de texto.
     * @return {@code true} si la cadena esta acomillada, caso contrario
     * {@code false}.
     */
    public static boolean isDoubleQuoted(String txt) {
        return isQuoted(txt, '\"');
    }

    /**
     * Determina si la representacion en cadena de un objeto esta acomillada.
     *
     * @param obj Objeto.
     * @param quotation0 Caracter de acomillado.
     * @return Si la representacion en cadena de texto esta acomillada.
     */
    public static boolean isQuoted(Object obj, Character quotation0) {
        String quotataion = String.valueOf(quotation0);
        String txt = String.valueOf(obj);
        return txt.startsWith(quotataion) && txt.endsWith(quotataion);
    }

    /**
     * Retorna una cadena "acomillada" con un caracter por defecto.
     *
     * @param obj Objeto a representar como texto.
     * @return Cadena acomillada.
     */
    public static String quoteSingle(Object obj) {
        return quote(obj, '\'');
    }

    /**
     * Retorna una cadena "acomillada" con un caracter por defecto.
     *
     * @param obj Objeto a representar como texto.
     * @return Cadena acomillada.
     */
    public static String quoteDouble(Object obj) {
        return quote(obj, '\"');
    }

    /**
     * Retorna una cadena "acomillada", verificando que la misma no este
     * previamente acomillada.
     *
     * @param txt Cadena de texto.
     * @return Cadena acomillada.
     */
    public static String safeQuoteSingle(String txt) {
        if (isSingleQuoted(txt)) {
            return txt;
        } else {
            return quoteSingle(txt);
        }
    }

    /**
     * Retorna una cadena "acomillada", verificando que la misma no este
     * previamente acomillada.
     *
     * @param txt Cadena de texto.
     * @return Cadena acomillada.
     */
    public static String safeQuoteDouble(String txt) {
        if (isDoubleQuoted(txt)) {
            return txt;
        } else {
            return quoteDouble(txt);
        }
    }

    /**
     * Retorna una cadena "acomillada" con un caracter dado.
     *
     * @param obj Objeto a representar como texto.
     * @param quotation Caracter de acomillado.
     * @return Cadena acomillada.
     */
    public static String quote(Object obj, Character quotation) {
        return Strings.format("%c%s%c", quotation, String.valueOf(obj), quotation);
    }

    /**
     * Obtiene una representacion normalizada para cadenas de texto. Si la
     * cadena es vacia o nula, se retorna un texto arbitrario como relleno.
     *
     * @param text Texto a normalizar.
     * @return Texto normalizado.
     */
    public static String normalize(String text) {
        return select(text, nd());
    }

    /**
     * Reemplaza todas las ocurrencias de un patron de texto por otro. La fase
     * de reemplazo se aplica varias veces hasta que no exista ninguna
     * coincidencia del patron de texto que se desea reemplazar.
     *
     * @param text Texto original.
     * @param replace Patron de texto a reemplazar.
     * @param replacee Patron de texto de reemplazo.
     * @return Texto modificado.
     */
    public static String replaceAll(String text, String replace, String replacee) {
        String a = text;
        String b = text.replace(replace, replacee);
        while (!Objects.equals(a, b)) {
            a = b;
            b = b.replace(replace, replacee);
        }
        return a.trim();
    }

    /**
     * Convierte todas las secuencias de dos barras '//' a una sola barra '/'
     * dentro de una cadena de texto.
     *
     * @param text Cadena de texto.
     * @return Cadena de texto normalizada.
     */
    public static String normalizeSlashes(String text) {
        return Text.replaceAll(text, "//", "/");
    }

    /**
     * Separa una cadena de texto
     * {@code nombre=valor&nombre=valor&...&nombre=valor} en sus
     * correspondientes partes.
     *
     * @param query Cadena de texto en formato
     * {@code nombre=valor&nombre=valor&...&nombre=valor}.
     * @return Coleccion de parametros agrupados por {@code nombre}.
     */
    public static Map<String, String> parseQuerystring(String query) {
        Map<String, String> params = new LinkedHashMap<>();

        // Control de seguridad.
        if (query == null || query.isEmpty()) {
            return params;
        }

        // Parsear elementos de parametro.
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            try {
                int pos = pair.indexOf("=");
                String name = URLDecoder.decode(pair.substring(0, pos), Charsets.UTF8.name());
                String value = URLDecoder.decode(pair.substring(pos + 1), Charsets.UTF8.name());
                params.put(name, value);
            } catch (UnsupportedEncodingException thr) {
                Errors.dump(System.err, thr, "Ocurrió un error procesando elemento de parámetro '%s'", pair);
            }
        }

        return params;
    }

    /**
     * Permite retornar la primera cadena no vacia entre dos cadenas
     * arbitrarias.
     *
     * @param a Texto.
     * @param b Texto.
     * @return Texto no vacio.
     */
    public static String select(String a, String b) {
        if (!Assertions.stringNullOrEmpty(a)) {
            return a;
        }

        if (!Assertions.stringNullOrEmpty(b)) {
            return b;
        }

        return nd();
    }

}
