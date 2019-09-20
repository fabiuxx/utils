/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SHA256 {

    public static final String ALGORITHM_FAMILY = "SHA-256";

    public static final int ALGORITHM_KEY_SIZE_BYTES = 32;

    public static byte[] encode(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM_FAMILY);
        byte[] hash = digest.digest(bytes);
        return hash;
    }

}
