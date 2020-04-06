/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.crypto.SHA256;
import fa.gs.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Hashes {

    public static byte[] sha256(byte[] bytes) throws Throwable {
        return SHA256.encode(bytes);
    }

    public static String sha256AsHexString(byte[] bytes) throws Throwable {
        byte[] hash = sha256(bytes);
        return Strings.bytesToHexString(hash).toLowerCase().trim();
    }

}
