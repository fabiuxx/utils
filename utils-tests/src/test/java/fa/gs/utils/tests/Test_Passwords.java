/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.authentication.passwords.PasswordVerify;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Passwords {

    public static final String SHA256_HASH = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3";

    public static final String[] BCRYPT_HASHES = {
        "$2a$10$sRrYju0KxxiVPXpjv.jIr.Kb2kGBxkIbwPKLp08WcxFKcZce3CaD6",
        "$2a$10$id2obbmuUNlhZPPheLxeoO2NLc7R.00jv4j4NkO2bMjsbR.TSiqTu",
        "$2a$10$.NRwUZpS0Xt8gt810iNZm.gxk/XqwgjOm0.bAex55BCld3yrkhasi",
        "$2a$10$F9dKNgllup4WvNrXVCwbvOxnvit2plNTsDFsZOzYzphqEs6LWaVxa",
        "$2a$05$AWlHxDwwykZ9tVNaq4P.puJpT4INVH8x1O42wv9ZyweB0G3Y6ZWVW"
    };

    public static final String PLAIN_TEXT = "123";

    @Test
    public void test0() throws Throwable {
        boolean ok = PasswordVerify.sha256(PLAIN_TEXT, SHA256_HASH);
        Assertions.assertTrue(ok);
    }

    @Test
    public void test1() throws Throwable {
        for (String BCRYPT_HASH : BCRYPT_HASHES) {
            boolean ok = PasswordVerify.bcrypt(PLAIN_TEXT, BCRYPT_HASH);
            Assertions.assertTrue(ok);
        }
    }

}
