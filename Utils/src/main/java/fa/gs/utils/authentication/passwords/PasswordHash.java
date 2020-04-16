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
public class PasswordHash {

    public static String sha256(String plain) throws Throwable {
        PasswordHasher hasher = new PasswordHasher_SHA256();
        return hasher.hash(plain);
    }

    public static String bcrypt(String plain) throws Throwable {
        PasswordHasher hasher = new PasswordHasher_BCRYPT();
        return hasher.hash(plain);
    }

}
