/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.crypto;

import py.com.generica.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Base64 {

    private static final int FLAGS = Base64Impl.NO_WRAP | Base64Impl.NO_PADDING;

    // texto plano a texto en base64.
    public static String encode(String text) {
        byte[] bytes = encodeFromString(text);
        return Strings.getString(bytes);
    }

    // texto plano a bytes de texto en base 64.
    public static byte[] encodeFromString(String text) {
        byte[] bytes = Strings.getBytes(text);
        return encode(bytes);
    }

    // bytes de texto plano a bytes de texto en base64.
    public static byte[] encode(byte[] bytes) {
        return Base64Impl.encode(bytes, FLAGS);
    }

    // bytes de texto plano a texto en base 64.
    public static String encodeToString(byte[] bytes) {
        byte[] b64 = encode(bytes);
        return Strings.getString(b64);
    }

    // texto en base 64 a texto plano.
    public static String decode(String text) {
        byte[] bytes = decodeFromString(text);
        return Strings.getString(bytes);
    }

    // texto en base 64 a bytes de texto plano.
    public static byte[] decodeFromString(String b64) {
        byte[] bytes = Strings.getBytes(b64);
        return decode(bytes);
    }

    // bytes de texto en base64 a bytes de texto plano.
    public static byte[] decode(byte[] bytes) {
        return Base64Impl.decode(bytes, FLAGS);
    }

    // bytes de texto en base 64 a texto plano.
    public static String decodeToString(byte[] bytes) {
        byte[] text = decode(bytes);
        return Strings.getString(text);
    }

}
