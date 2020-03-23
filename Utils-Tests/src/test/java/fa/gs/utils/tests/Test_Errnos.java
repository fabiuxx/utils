/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.misc.errors.Errno;
import fa.gs.utils.misc.errors.Errors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Errnos {

    @Test
    public void test0() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("", "");
        });
    }

    @Test
    public void test1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("A", "");
        });
    }

    @Test
    public void test2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("AB", "");
        });
    }

    @Test
    public void test3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("ABC", "");
        });
    }

    @Test
    public void test4() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("ABC", "0");
        });
    }

    @Test
    public void test5() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("ABC", "00000");
        });
    }

    @Test
    public void test6() {
        Errno errno = Errors.errno("ABC", "000000");
        Assertions.assertEquals("ABC000000", errno.getErrnoString());
    }

    @Test
    public void test7() {
        Errno errno = Errors.errno("ABC", 0);
        Assertions.assertEquals("ABC000000", errno.getErrnoString());
    }

    @Test
    public void test8() {
        Errno errno = Errors.errno("ABC", 100);
        Assertions.assertEquals("ABC000100", errno.getErrnoString());
    }

    @Test
    public void test9() {
        Errno errnoA = Errors.errno("ABC", 100);
        Errno errnoB = Errors.errno("ABC", "000100");
        Assertions.assertEquals(errnoA.getErrnoString(), errnoB.getErrnoString());
    }

    @Test
    public void test10() {
        Errno errno = Errors.errno("ABC", 999999);
        Assertions.assertEquals("ABC999999", errno.getErrnoString());
    }

    @Test
    public void test11() {
        Errno errno = Errors.errno("ABC", 999999 + 1);
        Assertions.assertEquals("ABC999999", errno.getErrnoString());
    }

    @Test
    public void test12() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("");
        });
    }

    @Test
    public void test13() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("A");
        });
    }

    @Test
    public void test14() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errors.errno("AA");
        });
    }

    @Test
    public void test15() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Errno errno = Errors.errno("AAAB");
            boolean a = "AAA".equals(errno.getCode());
            boolean b = "B".equals(errno.getDescriptor());
            Assertions.assertTrue(a && b);
        });
    }

    @Test
    public void test16() {
        Errno errno = Errors.errno("AAABBBBBB");
        boolean a = "AAA".equals(errno.getDescriptor());
        boolean b = "BBBBBB".equals(errno.getCode());
        Assertions.assertTrue(a && b);
    }

}
