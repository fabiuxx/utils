/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.authentication.passwords.PasswordVerifier;
import fa.gs.utils.authentication.passwords.PasswordVerifier_BCRYPT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Passwords {

    public static final String BCRYPT_HASH = "$2a$10$sRrYju0KxxiVPXpjv.jIr.Kb2kGBxkIbwPKLp08WcxFKcZce3CaD6";

    public static final String PLAIN_TEXT = "123";

    @Test
    public void test0() throws Throwable {
        PasswordVerifier verifier = new PasswordVerifier_BCRYPT();
        boolean ok = verifier.verifies(PLAIN_TEXT, BCRYPT_HASH);
        Assertions.assertTrue(ok);
    }

}
