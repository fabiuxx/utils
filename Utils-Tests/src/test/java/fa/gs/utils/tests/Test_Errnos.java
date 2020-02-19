/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.misc.errors.Errno;
import fa.gs.utils.misc.errors.Errors;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Errnos {

    @Test(expected = IllegalArgumentException.class)
    public void test0() {
        Errors.errno("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test1() {
        Errors.errno("A", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test2() {
        Errors.errno("AB", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test3() {
        Errors.errno("ABC", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test4() {
        Errors.errno("ABC", "0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test5() {
        Errors.errno("ABC", "00000");
    }

    @Test
    public void test6() {
        Errno errno = Errors.errno("ABC", "000000");
        Assert.assertEquals("ABC000000", errno.getErrnoString());
    }

    @Test
    public void test7() {
        Errno errno = Errors.errno("ABC", 0);
        Assert.assertEquals("ABC000000", errno.getErrnoString());
    }

    @Test
    public void test8() {
        Errno errno = Errors.errno("ABC", 100);
        Assert.assertEquals("ABC000100", errno.getErrnoString());
    }

    @Test
    public void test9() {
        Errno errnoA = Errors.errno("ABC", 100);
        Errno errnoB = Errors.errno("ABC", "000100");
        Assert.assertEquals(errnoA.getErrnoString(), errnoB.getErrnoString());
    }

    @Test
    public void test10() {
        Errno errno = Errors.errno("ABC", 999999);
        Assert.assertEquals("ABC999999", errno.getErrnoString());
    }

    @Test
    public void test11() {
        Errno errno = Errors.errno("ABC", 999999 + 1);
        Assert.assertEquals("ABC999999", errno.getErrnoString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test12() {
        Errors.errno("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test13() {
        Errors.errno("A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test14() {
        Errors.errno("AA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test15() {
        Errno errno = Errors.errno("AAAB");
        boolean a = "AAA".equals(errno.getCode());
        boolean b = "B".equals(errno.getDescriptor());
        Assert.assertTrue(a && b);
    }

    @Test
    public void test16() {
        Errno errno = Errors.errno("AAABBBBBB");
        boolean a = "AAA".equals(errno.getDescriptor());
        boolean b = "BBBBBB".equals(errno.getCode());
        Assert.assertTrue(a && b);
    }

}
