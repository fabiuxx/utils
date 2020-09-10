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
public class Cipher {

    public static String encrypt(Cipher_AES aes, String text) throws Throwable {
        byte[] bytes = Strings.getBytes(text);
        byte[] ec = aes.encrypt(bytes);
        return Strings.bytesToHexString(ec);
    }

    public static String decrypt(Cipher_AES aes, String text) throws Throwable {
        byte[] bytes = Strings.hexStringToBytes(text);
        byte[] dc = aes.decrypt(bytes);
        return Strings.getString(dc).trim();
    }

}
