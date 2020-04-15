/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Buffer {

    public static int len(final char[] buffer) {
        if (buffer == null) {
            throw Errors.illegalArgument("El parametro no puede ser nulo");
        }

        int len = 0;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] == '\0' || buffer[i] == (char) 0) {
                break;
            }
            len++;
        }
        return len;
    }

    public static void set(char[] buffer, char value, int buffer_len) {
        for (int i = 0; i < buffer_len; i++) {
            buffer[i] = value;
        }
    }

    public static int copy(char[] target, final char[] source, int source_len) {
        return copy(target, 0, source, 0, source_len);
    }

    public static int copy(char[] target, int target_pos, final char[] source, int source_pos, int source_len) {
        int i;
        for (i = 0; i < source_len; i++) {
            target[i + target_pos] = source[i + source_pos];
        }
        return target_pos + source_len;
    }

    public static int compare(char[] buffer_a, final char[] buffer_b, int buffer_len) {
        int i;
        for (i = 0; i < buffer_len; i++) {
            char a = buffer_a[i];
            char b = buffer_b[i];
            if (a != b) {
                return 0;
            }
        }
        return 1;
    }

    private static int hex_char_to_int(char character) {
        int result;
        if (character >= '0' && character <= '9') {
            result = character - '0';
        } else if (character >= 'a' && character <= 'f') {
            result = character - 'a' + 10;
        } else if (character >= 'A' && character <= 'F') {
            result = character - 'A' + 10;
        } else {
            result = 0;
        }
        return result;
    }

    static char int_to_hex_char(char number) {
        char result;
        result = Strings.HEX_DIGITS[number];
        return result;
    }

    public static byte[] hex_char_array_to_byte_array(final char[] buffer, int buffer_len) {
        // Se asume que el buffer de entrada tiene un tamanho multiplo de 2.
        byte[] output = new byte[buffer_len / 2];

        for (int i = 0, j = 0; i < buffer_len; i += 2, j++) {
            int a = hex_char_to_int(buffer[i]);
            int b = hex_char_to_int(buffer[i + 1]);
            int c = ((a << 4) | (b & 0x0F));
            output[j] = (byte) c;
        }

        return output;
    }

    public static char[] byte_array_to_hex_char_array(final byte[] buffer, int buffer_len) {
        // Se asume que el buffer de entrada tiene un tamanho multiplo de 2.
        char[] output = new char[buffer_len * 2];

        for (int i = 0, j = 0; i < buffer_len; i++, j += 2) {
            byte c = buffer[i];
            char a = int_to_hex_char((char) ((c & 0xF0) >> 4));
            char b = int_to_hex_char((char) (c & 0x0F));
            output[j] = a;
            output[j + 1] = b;
        }

        return output;
    }
}
