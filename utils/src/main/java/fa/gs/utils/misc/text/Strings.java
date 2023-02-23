/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.text;

import fa.gs.utils.misc.Assertions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Strings {

    public static final char[] HEX_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static String random(int length, String alphabet) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static String guard(String value) {
        if (value == null) {
            return "";
        } else {
            return value.trim();
        }
    }

    /**
     * Retorna la primera cadena no nula ni vacia de un conjunto de valores
     * posibles.
     *
     * @param values Conjunto de valores posibles.
     * @return Cadena no vacia, si hubiere.
     */
    public static String coalesce(String... values) {
        return coalesce(false, values);
    }

    /**
     * Retorna la primera cadena no nula ni vacia de un conjunto de valores
     * posibles.
     *
     * @param acceptEmptyString Si se puede considerar el valor vacio como un
     * valor valido.
     * @param values Conjunto de valores posibles.
     * @return Cadena no vacia, si hubiere.
     */
    public static String coalesce(boolean acceptEmptyString, String... values) {
        if (!Assertions.isNullOrEmpty(values)) {
            for (String value : values) {
                if (value.isEmpty() && acceptEmptyString) {
                    return value;
                }
                if (!Assertions.stringNullOrEmpty(value)) {
                    return value;
                }
            }
        }
        return null;
    }

    public static String truncate(String value, int len) {
        if (Assertions.stringNullOrEmpty(value)) {
            return value;
        } else if (value.length() > len) {
            return value.substring(0, len);
        } else {
            return value;
        }
    }

    /**
     * Formatea una cadena y sus argumentos, si hubieren.
     *
     * @param fmt Formato de cadena.
     * @param args Argumentos de formateo de cadena, que se consideran solo si
     * existe al menos uno.
     * @return Cadena formateada.
     */
    public static String format(String fmt, Object... args) {
        if (Assertions.stringNullOrEmpty(fmt)) {
            return "";
        }

        if (args == null || args.length == 0) {
            return fmt;
        } else {
            return String.format(fmt, args);
        }
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

    public static InputStream getStream(String text) {
        return new ByteArrayInputStream(getBytes(text));
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

    public static final String getString(InputStream stream) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4098];
            int read;
            while ((read = stream.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            byte[] bytes = baos.toByteArray();
            return getString(bytes);
        }
    }

    public static String bytesToHexString(byte[] bytes) {
        return bytesToHexString(bytes, 0, bytes.length);
    }

    public static String bytesToHexString(byte[] bytes, int offset, int len) {
        char[] buf = new char[len * 2];
        for (int i = offset, j = 0, k; i < len + offset;) {
            k = bytes[i++];
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
