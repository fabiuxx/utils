/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.passwords;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PasswordVerify {

    public static boolean sha256(String plain, String hashed) throws Throwable {
        PasswordVerifier verifier = new PasswordVerifier_SHA256();
        return verifier.verifies(plain, hashed);
    }

    public static boolean bcrypt(String plain, String hashed) throws Throwable {
        PasswordVerifier verifier = new PasswordVerifier_BCRYPT();
        return verifier.verifies(plain, hashed);
    }

}
