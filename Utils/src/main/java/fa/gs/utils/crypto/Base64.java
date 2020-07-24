/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.crypto;

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
        org.apache.commons.codec.binary.Base64 encoder = new org.apache.commons.codec.binary.Base64(true);
        byte[] b64 = encoder.encode(plain);
        return Strings.getString(b64);
    }

    public static byte[] decodeString(String b64) {
        org.apache.commons.codec.binary.Base64 decoder = new org.apache.commons.codec.binary.Base64(true);
        byte[] bytes = decoder.decode(b64);
        return bytes;
    }

    public static byte[] decodeBytes(byte[] b64) {
        org.apache.commons.codec.binary.Base64 decoder = new org.apache.commons.codec.binary.Base64(true);
        byte[] bytes = decoder.decode(b64);
        return bytes;
    }

}
