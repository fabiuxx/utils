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

    public static void alloc(StringBuilder builder, Character c, int len) {
        while (len > 0) {
            builder.append(c);
            len--;
        }
    }

    public static String left(String text, Character pad, int len) {
        int L = text.length();
        if (L > len) {
            return text;
        } else {
            int d = Math.max(0, len - L);
            return pad(text, pad, len, d, 0);
        }
    }

    public static String right(String text, Character pad, int len) {
        int L = text.length();
        if (L > len) {
            return text;
        } else {
            int d = Math.max(0, len - L);
            return pad(text, pad, len, 0, d);
        }
    }

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
