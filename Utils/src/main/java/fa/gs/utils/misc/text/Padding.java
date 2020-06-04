/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.text;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Padding {

    /**
     * Agrega una cadena de tamanho {@code len}, compuesta netamente de
     * caracteres {@code c}.
     *
     * @param builder Constructor de cadenas.
     * @param c Caracter de relleno.
     * @param len Cantidad de caracteres a agregar.
     */
    private static void alloc(StringBuilder builder, Character c, int len) {
        while (len > 0) {
            builder.append(c);
            len--;
        }
    }

    /**
     * Agrega un padding izquierdo hasta completar el tamanho {@code len} si la
     * cadena propocionada tiene una longitud inferior.
     *
     * @param text Texto original.
     * @param pad Caracter de relleno.
     * @param len Tamnho final de texto con padding.
     * @return Texto con padding.
     */
    public static String left(String text, Character pad, int len) {
        int L = text.length();
        if (L > len) {
            return text;
        } else {
            int d = Math.max(0, len - L);
            return pad(text, pad, len, d, 0);
        }
    }

    /**
     * Agrega un padding derecho hasta completar el tamanho {@code len} si la
     * cadena propocionada tiene una longitud inferior.
     *
     * @param text Texto original.
     * @param pad Caracter de relleno.
     * @param len Tamnho final de texto con padding.
     * @return Texto con padding.
     */
    public static String right(String text, Character pad, int len) {
        int L = text.length();
        if (L > len) {
            return text;
        } else {
            int d = Math.max(0, len - L);
            return pad(text, pad, len, 0, d);
        }
    }

    /**
     * Agrega un padding lateral hasta completar el tamanho {@code len} si la
     * cadena propocionada tiene una longitud inferior. Se busca ubicar el texto
     * original lo mas al centro posible.
     *
     * @param text Texto original.
     * @param pad Caracter de relleno.
     * @param len Tamnho final de texto con padding.
     * @return Texto con padding.
     */
    public static String center(String text, Character pad, int len) {
        int L = text.length();
        if (L > len) {
            return text;
        } else {
            int d = Math.max(0, len - L);
            int a = Math.round(d / 2);
            int b = Math.max(0, d - a);
            return pad(text, pad, len, a, b);
        }
    }

    /**
     * Agrega un padding lateral hasta completar el tamanho {@code len} si la
     * cadena propocionada tiene una longitud inferior.
     *
     * @param text Texto original.
     * @param pad Caracter de relleno.
     * @param len Tamanho final de texto con padding.
     * @param left Tamanho para padding izquierdo.
     * @param right Tamanho para padding derecho.
     * @return Texto con padding.
     */
    public static String pad(String text, Character pad, int len, int left, int right) {
        int L = text.length();
        if ((L + left + right) > len) {
            return text;
        } else {
            StringBuilder padded = new StringBuilder();
            alloc(padded, pad, left);
            padded.append(text);
            alloc(padded, pad, right);
            return padded.toString();
        }
    }

}
