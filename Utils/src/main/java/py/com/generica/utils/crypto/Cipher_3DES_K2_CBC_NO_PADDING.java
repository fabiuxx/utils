/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.crypto;

import java.security.Security;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import py.com.generica.utils.misc.text.Strings;

/**
 * https://ttux.net/post/3des-java-encrypter-des-java-encryption/
 *
 * @author Fabio A. González Sosa
 */
public class Cipher_3DES_K2_CBC_NO_PADDING {

    static {
        // Instalar Proveedor de servicios de seguridad de Bouncy Castle.
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Vector de Inicializacion por defecto.
     */
    private static final byte[] DEFAULT_IV = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * Nombre del algoritmo de generacion de claves.
     */
    private static final String TRIPLE_DES_KEY_FACTORY_ALGORITHM_NAME = "DESede";

    /**
     * Nombre del algoritmo de transformacion de bloques.
     * 3DES_K2_CBC_NO_PADDING.
     */
    private static final String TRIPLE_DES_CIPHER_TRANSFORMATION_NAME = "DESede/CBC/NoPadding";

    /**
     * Nombre del proveedor de servicios de seguridad.
     */
    private static final String SECURITY_PROVIDER_NAME = "SunJCE";

    /**
     * Clave secreta.
     */
    private final SecretKey key;

    /**
     * Vectori de inicialiacion para el cifrado.
     */
    private final IvParameterSpec iv;
    //</editor-fold>

    /**
     * Constructor.
     *
     * @param keyBytes Bytes que componen la clave de cifrado. Debe ser de 32
     * bytes.
     * @param ivBytes Bytes que componen el vector de inicializacion. Debe ser
     * de 16 bytes.
     * @throws Exception Si alguno de los datos es incorrecto.
     */
    private Cipher_3DES_K2_CBC_NO_PADDING(byte[] keyBytes, byte[] ivBytes) throws Exception {
        KeySpec keySpec = new DESedeKeySpec(keyBytes);
        key = SecretKeyFactory.getInstance(TRIPLE_DES_KEY_FACTORY_ALGORITHM_NAME).generateSecret(keySpec);
        iv = new IvParameterSpec(ivBytes);
    }

    /**
     * Obtiene un constructor especifico para este algoritmo de cifrado.
     *
     * @return Constructor del algoritmo de cifrado.
     */
    public static Cipher_3DES_K2_CBC_NO_PADDING.Builder builder() {
        return new Cipher_3DES_K2_CBC_NO_PADDING.Builder();
    }

    /**
     * Encripta contenido.
     *
     * @param content Bytes que componen el contenido a encriptar.
     * @return Bytes cifrados.
     */
    public byte[] encrypt(byte[] content) {
        try {
            Cipher cipher = Cipher.getInstance(TRIPLE_DES_CIPHER_TRANSFORMATION_NAME, SECURITY_PROVIDER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            return cipher.doFinal(content);
        } catch (Throwable thr) {
            thr.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * Desencripta contenido, idealmente, encriptado.
     *
     * @param content Bytes que componen el contenido a desencriptar.
     * @return Bytes descifrados.
     */
    public byte[] decrypt(byte[] content) {
        try {
            Cipher cipher = Cipher.getInstance(TRIPLE_DES_CIPHER_TRANSFORMATION_NAME, BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            return cipher.doFinal(content);
        } catch (Throwable thr) {
            thr.printStackTrace(System.err);
            return null;
        }
    }

    public static class Builder {

        private byte[] keyBytes;
        private byte[] ivBytes;

        private Builder() {
            this.keyBytes = null;
            this.ivBytes = Cipher_3DES_K2_CBC_NO_PADDING.DEFAULT_IV;
        }

        public Builder key(String key) {
            return key(Strings.hexStringToBytes(key));
        }

        public Builder key(byte[] key) {
            this.keyBytes = key;
            return this;
        }

        public Builder iv(String iv) {
            return iv(Strings.hexStringToBytes(iv));
        }

        public Builder iv(byte[] iv) {
            this.ivBytes = iv;
            return this;
        }

        public Cipher_3DES_K2_CBC_NO_PADDING build() throws Exception {
            Cipher_3DES_K2_CBC_NO_PADDING cipher = new Cipher_3DES_K2_CBC_NO_PADDING(keyBytes, ivBytes);
            return cipher;
        }
    }

}
