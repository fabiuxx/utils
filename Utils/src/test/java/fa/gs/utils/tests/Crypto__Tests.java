/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.crypto.Cipher;
import fa.gs.utils.crypto.Cipher_AES;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Crypto__Tests {

    private static byte[] TEST_KEY;

    private static byte[] TEST_IV0;

    private static byte[] TEST_IV1;

    private static String TEXT;

    @BeforeClass
    public static void init() {
        TEST_KEY = "0934oiuwadasdhjk23yqwydaksj9qls7".getBytes(StandardCharsets.UTF_8);
        TEST_IV0 = "0000000000000000".getBytes(StandardCharsets.UTF_8);
        TEST_IV1 = "oqiwueuyasiduyqw".getBytes(StandardCharsets.UTF_8);
        TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas turpis odio, pharetra a metus eu, consectetur ornare sapien. Duis at facilisis mauris, nec scelerisque mi. Aenean a nunc velit. Cras non velit id justo hendrerit ornare. In vel dapibus ante, id consequat metus. Cras mollis tortor vel mi imperdiet, sit amet fringilla mi tincidunt. Quisque porttitor diam ac nisl dictum, id efficitur sapien vehicula. Fusce malesuada in felis eu ultrices. Etiam volutpat ante eu vulputate volutpat. Aliquam dictum tortor lobortis diam tincidunt, a eleifend metus condimentum. Duis egestas elit eu augue ultricies gravida. In consectetur, purus at ornare ultrices, ligula felis condimentum turpis, a efficitur risus tellus nec dui.";
    }

    @Test
    public void test0() throws Throwable {
        Cipher_AES aes = Cipher_AES.instance(TEST_KEY);
        String c0 = Cipher.encrypt(aes, TEXT);
        String text = Cipher.decrypt(aes, c0);
        boolean equals = text.equals(TEXT);
        Assert.assertTrue(equals);
    }

    @Test
    public void test1() throws Throwable {
        Cipher_AES aes = Cipher_AES.instance(TEST_KEY, TEST_IV0);
        String c0 = Cipher.encrypt(aes, TEXT);
        String text = Cipher.decrypt(aes, c0);
        boolean equals = text.equals(TEXT);
        Assert.assertTrue(equals);
    }

    @Test
    public void test2() throws Throwable {
        Cipher_AES aes = Cipher_AES.instance(TEST_KEY, TEST_IV1);
        String c0 = Cipher.encrypt(aes, TEXT);
        String text = Cipher.decrypt(aes, c0);
        boolean equals = text.equals(TEXT);
        Assert.assertTrue(equals);
    }

    @Test
    public void test3() throws Throwable {
        Cipher_AES aes1 = Cipher_AES.instance(TEST_KEY, TEST_IV0);
        String c1 = Cipher.encrypt(aes1, TEXT);

        Cipher_AES aes2 = Cipher_AES.instance(TEST_KEY, TEST_IV1);
        String c2 = Cipher.encrypt(aes2, TEXT);

        boolean equals = c1.equals(c2);
        Assert.assertFalse(equals);
    }

    @Test
    public void test4() throws Throwable {
        Cipher_AES aes1 = Cipher_AES.instance(TEST_KEY, TEST_IV0);
        String c1 = Cipher.encrypt(aes1, TEXT);
        String p1 = Cipher.decrypt(aes1, c1);

        Cipher_AES aes2 = Cipher_AES.instance(TEST_KEY, TEST_IV1);
        String c2 = Cipher.encrypt(aes2, TEXT);
        String p2 = Cipher.decrypt(aes2, c2);

        boolean equals = p1.equals(p2);
        Assert.assertTrue(equals);
    }

}
