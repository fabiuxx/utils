/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.crypto;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Cipher_AES {

    private static final byte[] IV0 = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };

    private BufferedBlockCipher cipher;
    private CipherParameters params;

    private Cipher_AES(byte[] key, byte[] iv) {
        this.cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        this.params = new ParametersWithIV(new KeyParameter(key), iv);
    }

    public static Cipher_AES instance(byte[] key) {
        return instance(key, IV0);
    }

    public static Cipher_AES instance(byte[] key, byte[] iv) {
        return new Cipher_AES(key, iv);
    }

    public byte[] encrypt(byte[] input) throws Throwable {
        return process(input, true);
    }

    public byte[] decrypt(byte[] input) throws Throwable {
        return process(input, false);
    }

    private byte[] process(byte[] input, boolean encrypt) throws DataLengthException, InvalidCipherTextException {
        cipher.init(encrypt, params);
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int bytesWrittenOut = cipher.processBytes(input, 0, input.length, output, 0);
        cipher.doFinal(output, bytesWrittenOut);
        return output;

    }

}
