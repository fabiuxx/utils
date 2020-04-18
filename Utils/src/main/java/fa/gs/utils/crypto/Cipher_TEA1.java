/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.crypto;

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

    /**
     * Clave de ejemplo, puede utilizarse por defecto en caso de no necesitar
     * una clave generada desde cero.
     */
    public static final int[] TEA_EXAMPLE_CIPHER_KEY = {
        0x37ffbab9, 0x3c2cec4f, 0x9f203042, 0x73c0157b,
        0x8519678e, 0x073b8a36, 0xa95f1e96, 0x1c72e7b3,
        0xf76991da, 0x6e452c30, 0x1d56b1bc, 0x94f8f69e,
        0xc3c113d6, 0x137af54b, 0x5550d56f, 0x3291e0d4,
        0x96d7f531, 0x674fdb85, 0x6460e17e, 0x69dd1877,
        0xffcdf8a0, 0x5e44e713, 0xbec49565, 0x6bb0ba07,
        0xc189c66c, 0xae6beb71, 0xecec2bba, 0xdb24b32e,
        0xf39f6622, 0x8e789f64, 0xdd5de8b7, 0xe799ed0e
    };

    private final static int DELTA = 0x9e3779b9;
    private final static int DECRYPT_SUM_INIT = 0xC6EF3720;
    private final static long MASK32 = (1L << 32) - 1;
    private final int[] key;

    private Cipher_TEA1(int[] key) {
        this.key = key;
    }

    public static Cipher_TEA1 instance() {
        return instance(TEA_EXAMPLE_CIPHER_KEY);
    }

    public static Cipher_TEA1 instance(int[] key) {
        return new Cipher_TEA1(key);
    }

    public long encrypt(long input) {
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

    public long decrypt(long input) {
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
