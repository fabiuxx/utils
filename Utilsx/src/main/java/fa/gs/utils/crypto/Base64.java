/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.crypto;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Base64 {

    public static String encodeString(String plain) {
        return fa.gs.utils.android.crypto.Base64.encodeString(plain);
    }

    public static String encodeBytes(byte[] plain) {
        return fa.gs.utils.android.crypto.Base64.encodeBytes(plain);
    }

    public static byte[] decodeString(String b64) {
        return fa.gs.utils.android.crypto.Base64.decodeString(b64);
    }

    public static byte[] decodeBytes(byte[] b64) {
        return fa.gs.utils.android.crypto.Base64.decodeBytes(b64);
    }

}
