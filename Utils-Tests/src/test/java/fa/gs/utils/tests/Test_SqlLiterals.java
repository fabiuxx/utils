/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.database.query.expressions.literals.SQLStringLiterals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_SqlLiterals {

    static String SEPARATOR = " ";

    @Test
    public void test0() {
        String txt = SQLStringLiterals.contains("");
        Assertions.assertEquals("", txt);
    }

    @Test
    public void test1() {
        String txt = SQLStringLiterals.contains("a");
        Assertions.assertEquals("%a%", txt);
    }

    @Test
    public void test2() {
        String txt = SQLStringLiterals.contains("a b");
        Assertions.assertEquals("%a b%", txt);
    }

    @Test
    public void test3() {
        String txt = SQLStringLiterals.contains("", null);
        Assertions.assertEquals("", txt);
    }

    @Test
    public void test4() {
        String txt = SQLStringLiterals.contains("a", null);
        Assertions.assertEquals("%a%", txt);
    }

    @Test
    public void test5() {
        String txt = SQLStringLiterals.contains("a b", null);
        Assertions.assertEquals("%a b%", txt);
    }

    @Test
    public void test6() {
        String txt = SQLStringLiterals.contains("", SEPARATOR);
        Assertions.assertEquals("", txt);
    }

    @Test
    public void test7() {
        String txt = SQLStringLiterals.contains("a", SEPARATOR);
        Assertions.assertEquals("%a%", txt);
    }

    @Test
    public void test8() {
        String txt = SQLStringLiterals.contains("a b", SEPARATOR);
        Assertions.assertEquals("%a% %b%", txt);
    }

}
