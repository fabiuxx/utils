/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc.text;

import java.util.Objects;
import py.com.generica.utils.misc.Assertions;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Text {

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
     * Retorna una cadena "acomillada" con un caracter por defecto.
     *
     * @param obj Objeto a representar como texto.
     * @return Cadena acomillada.
     */
    public static String quote(Object obj) {
        return quote(obj, '\'');
    }

    /**
     * Retorna una cadena "acomillada" con un caracter dado.
     *
     * @param obj Objeto a representar como texto.
     * @param quotation Caracter de acomillado.
     * @return Cadena acomillada.
     */
    public static String quote(Object obj, Character quotation) {
        return String.format("%c%s%c", quotation, String.valueOf(obj), quotation);
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
     * Convierte todas las secuencias de dos barras '//' a una sola barra '/'
     * dentro de una cadena de texto.
     *
     * @param text Cadena de texto.
     * @return Cadena de texto normalizada.
     */
    public static String normalizeSlashes(String text) {
        String a = text;
        String b = text.replace("//", "/");
        while (!Objects.equals(a, b)) {
            a = b;
            b = b.replace("//", "/");
        }
        return a.trim();
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
