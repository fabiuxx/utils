/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.text;

import fa.gs.utils.misc.Assertions;

/**
 *
 * @author Acer
 */
public class Strings {

    public static final char[] HEX_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    /**
     * Formatea una cadena y sus argumentos, si hubieren.
     *
     * @param fmt Formato de cadena.
     * @param args Argumentos de formateo de cadena, que se consideran solo si
     * existe al menos uno.
     * @return Cadena formateada.
     */
    public static String format(String fmt, Object... args) {
        String msg = fmt;
        if (!Assertions.isNullOrEmpty(args)) {
            msg = String.format(fmt, args);
        }
        return msg;
    }

    /**
     * Obtiene los bytes que conforman un texto.
     *
     * @param text Cadena de texto.
     * @return Bytes.
     */
    public static final byte[] getBytes(String text) {
        if (text == null || text.isEmpty()) {
            return new byte[0];
        } else {
            return text.getBytes(Charsets.UTF8);
        }
    }

    /**
     * Obtiene una cadena conformada por los bytes indicados.
     *
     * @param bytes Bytes.
     * @return Cadena de texto.
     */
    public static final String getString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        } else {
            return new String(bytes, Charsets.UTF8);
        }
    }

    public static String bytesToHexString(byte[] ba) {
        int length = ba.length;
        char[] buf = new char[length * 2];
        for (int i = 0, j = 0, k; i < length;) {
            k = ba[i++];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        return new String(buf);
    }

    public static byte[] hexStringToBytes(String hex) {
        int len = hex.length();
        if (len == 0) {
            return null;
        }
        byte[] buf = new byte[((len + 1) / 2)];

        int i = 0, j = 0;
        if ((len % 2) == 1) {
            buf[j++] = (byte) charToHex(hex.charAt(i++));
        }

        while (i < len) {
            buf[j++] = (byte) ((charToHex(hex.charAt(i++)) << 4) | charToHex(hex.charAt(i++)));
        }
        return buf;
    }

    public static int charToHex(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }
        if (ch >= 'A' && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if (ch >= 'a' && ch <= 'f') {
            return ch - 'a' + 10;
        }
        return 0;
    }

}
