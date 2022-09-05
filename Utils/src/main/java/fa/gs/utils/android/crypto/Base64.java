/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.android.crypto;

import fa.gs.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Base64 {

    public static String encodeString(String plain) {
        byte[] bytes = Strings.getBytes(plain);
        return encodeBytes(bytes);
    }

    public static String encodeBytes(byte[] plain) {
        // Compatibilidad para versiones inferiores de apache commons codec.
        // Fuente: https://stackoverflow.com/a/2533538
        byte[] b64 = org.apache.commons.codec.binary.Base64.encodeBase64(plain);
        String B64 = Strings.getString(b64);
        return B64.replace('+', '-').replace('/', '_');
    }

    public static byte[] decodeString(String b64) {
        b64 = b64.replace('_', '/').replace('-', '+');
        byte[] bytes = Strings.getBytes(b64);
        return decodeBytes(bytes);
    }

    public static byte[] decodeBytes(byte[] b64) {
        return org.apache.commons.codec.binary.Base64.decodeBase64(b64);
    }

}
