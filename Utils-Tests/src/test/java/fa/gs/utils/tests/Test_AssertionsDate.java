/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.crypto.Cipher;
import fa.gs.utils.crypto.Cipher_AES;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_AssertionsDate {

    @Test
    public void test0() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 01, 01);
        Assertions.assertTrue(ok);
    }
    
    @Test
    public void test1() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 01, 30);
        Assertions.assertTrue(ok);
    }
    
    @Test
    public void test2() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 01, 31);
        Assertions.assertTrue(ok);
    }
    
    @Test
    public void test3() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 01, 32);
        Assertions.assertFalse(ok);
    }
    
    @Test
    public void test4() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, -1, 01);
        Assertions.assertFalse(ok);
    }
    
    @Test
    public void test5() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 0, 01);
        Assertions.assertFalse(ok);
    }
    
    @Test
    public void test6() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 13, 01);
        Assertions.assertFalse(ok);
    }
    
    @Test
    public void test7() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 02, 28);
        Assertions.assertTrue(ok);
    }
    
    @Test
    public void test8() throws Throwable {
        // Bisiesto.
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 02, 29);
        Assertions.assertTrue(ok);
    }
    
    @Test
    public void test9() throws Throwable {
        boolean ok = fa.gs.utils.misc.Assertions.isDate(2020, 02, 30);
        Assertions.assertFalse(ok);
    }


}
