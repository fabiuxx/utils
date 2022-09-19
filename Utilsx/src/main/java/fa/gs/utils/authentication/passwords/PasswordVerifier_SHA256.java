/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.passwords;

import fa.gs.utils.misc.Assertions;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PasswordVerifier_SHA256 implements PasswordVerifier {

    PasswordVerifier_SHA256() {
        ;
    }

    @Override
    public boolean verifies(String plain, String hashed) throws Throwable {
        PasswordHasher hasher = new PasswordHasher_SHA256();
        String hashed0 = hasher.hash(plain);
        return Assertions.equals(hashed0, hashed);
    }

}
