/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.crypto;

import java.math.BigInteger;

/**
 * Implementacion basica del algoritmo <i>Tiny Encryption Algorithm</i>.
 *
 * <p>
 * Fuentes:
 * </p>
 *
 * <ul>
 * <li>https://es.wikipedia.org/wiki/Tiny_Encryption_Algorithm</li>
 * <li>https://stackoverflow.com/a/32835073</li>
 * </ul>
 *
 * @author Fabio A. González Sosa
 */
public class Cipher_TEA1 {

    private final static int DELTA = 0x9e3779b9;
    private final static int DECRYPT_SUM_INIT = 0xC6EF3720;
    private final static long MASK32 = (1L << 32) - 1;

    public long encrypt(long input, int[] key) {
        int v1 = (int) input;
        int v0 = (int) (input >>> 32);
        int sum = 0;
        for (int i = 0; i < 32; i++) {
            sum += DELTA;
            v0 += ((v1 << 4) + key[0]) ^ (v1 + sum) ^ ((v1 >>> 5) + key[1]);
            v1 += ((v0 << 4) + key[2]) ^ (v0 + sum) ^ ((v0 >>> 5) + key[3]);
        }
        return (v0 & MASK32) << 32 | (v1 & MASK32);
    }

    public long decrypt(long input, int[] key) {
        int v1 = (int) input;
        int v0 = (int) (input >>> 32);
        int sum = DECRYPT_SUM_INIT;
        for (int i = 0; i < 32; i++) {
            v1 -= ((v0 << 4) + key[2]) ^ (v0 + sum) ^ ((v0 >>> 5) + key[3]);
            v0 -= ((v1 << 4) + key[0]) ^ (v1 + sum) ^ ((v1 >>> 5) + key[1]);
            sum -= DELTA;
        }
        return (v0 & MASK32) << 32 | (v1 & MASK32);
    }

    public static String long2string(long value) {
        String pad = "0000000000000000";
        String txt = Long.toHexString(value);
        return String.format("%s%s", pad.substring(txt.length()), txt);
    }

    public static long string2long(String value) {
        BigInteger num = new BigInteger(value, 16);
        return num.longValue();
    }
}
