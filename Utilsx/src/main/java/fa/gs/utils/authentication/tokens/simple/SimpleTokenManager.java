/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.tokens.simple;

import com.google.gson.JsonObject;
import fa.gs.utils.authentication.tokens.TokenDecoder;
import fa.gs.utils.authentication.tokens.TokenEncoder;
import fa.gs.utils.authentication.tokens.jwt.TokenClaim;
import fa.gs.utils.crypto.Cipher;
import fa.gs.utils.crypto.Cipher_AES;
import fa.gs.utils.misc.json.Json;
import fa.gs.utils.misc.text.Charsets;
import fa.gs.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SimpleTokenManager implements TokenEncoder<JsonObject>, TokenDecoder<JsonObject> {

    public static final String TOKEN_VERSION = "1";

    /**
     * Bytes que componen la clave de cifrado para la carga util que se
     * encuentra dentro de cada token JWT generado. Esta clave fue generada de
     * manera aleatoria.
     */
    private static final byte[] TOKEN_PAYLOAD_CIPHER_KEY = {
        (byte) 0x64, (byte) 0x62, (byte) 0x63, (byte) 0x29, (byte) 0x2a, (byte) 0x48, (byte) 0xc8, (byte) 0xd0,
        (byte) 0x97, (byte) 0x9a, (byte) 0x98, (byte) 0x12, (byte) 0x6f, (byte) 0xc4, (byte) 0xb3, (byte) 0xbc,
        (byte) 0x59, (byte) 0xa2, (byte) 0x84, (byte) 0xb6, (byte) 0x4a, (byte) 0x9d, (byte) 0x3d, (byte) 0x1c,
        (byte) 0x21, (byte) 0x1e, (byte) 0x24, (byte) 0x4e, (byte) 0x9a, (byte) 0x70, (byte) 0x00, (byte) 0x5f
    };

    @Override
    public String encodeToken(JsonObject payload) {
        try {
            JsonObject transformedPayload = encryptPayload(payload);
            String json = transformedPayload.toString();
            return Strings.bytesToHexString(json.getBytes(Charsets.UTF8));
        } catch (Throwable thr) {
            return "";
        }
    }

    @Override
    public JsonObject decodeToken(String token) {
        try {
            byte[] bytes = Strings.hexStringToBytes(token);
            String json = new String(bytes, Charsets.UTF8);
            JsonObject transformedPayload = Json.fromString(json).getAsJsonObject();
            return decryptPayload(transformedPayload);
        } catch (Throwable thr) {
            return null;
        }
    }

    /**
     * Transforma una carga util en una carga util especifica de esta version de
     * tokens.
     *
     * @param payload Carga util.
     * @return Carga util para esta version.
     */
    private static JsonObject encryptPayload(JsonObject payload) throws Throwable {
        String text = payload.toString();
        String data = Cipher.encrypt(getCipher(), text);

        JsonObject transformedPayload = new JsonObject();
        transformedPayload.addProperty(TokenClaim.VERSION.descripcion(), TOKEN_VERSION);
        transformedPayload.addProperty(TokenClaim.DATA.descripcion(), data);
        return transformedPayload;
    }

    /**
     * Obtiene la carga util original encapsulada dentro de una carga util para
     * esta version de tokens.
     *
     * @param payload Carga util para esta version, como objeto JSON.
     * @return Carga util original.
     */
    private static JsonObject decryptPayload(JsonObject payload) throws Throwable {
        String data = payload.get(TokenClaim.DATA.descripcion()).getAsString();
        String json = Cipher.decrypt(getCipher(), data);
        return Json.fromString(json).getAsJsonObject();
    }

    /**
     * Obtiene una instancia del cifrador de datos.
     *
     * @return Cifrador que utiliza el algoritmo AES 256.
     */
    private static Cipher_AES getCipher() {
        Cipher_AES aes = Cipher_AES.instance(TOKEN_PAYLOAD_CIPHER_KEY);
        return aes;
    }

}
