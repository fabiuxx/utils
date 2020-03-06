/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.tokens.jwt;

import com.google.gson.JsonObject;
import fa.gs.utils.authentication.tokens.TokenDecoder;
import fa.gs.utils.authentication.tokens.TokenEncoder;
import fa.gs.utils.crypto.Cipher;
import fa.gs.utils.crypto.Cipher_AES;
import fa.gs.utils.misc.json.Json;
import jwt4j.Algorithm;
import jwt4j.JWTHandler;
import jwt4j.JWTHandlerBuilder;
import jwt4j.JWTIdGenerator;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JwtTokenManager implements TokenEncoder<JsonObject>, TokenDecoder<JsonObject> {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Numero de version de token.
     */
    public static final String TOKEN_VERSION = "1";

    /**
     * Bytes que componen la clave de cifrado para la generacion de firma
     * digital del token JWT. Equivalen a los bytes, codificados en UTF-8, de la
     * cadena
     * "{@code ee218fa4-8068-4e08-9459-bddb9c69d12e-c12a1b52-0938-4463-bf24-7e9e51ab974c}".
     */
    private static final byte[] TOKEN_SIGNING_KEY = {
        (byte) 0x65, (byte) 0x65, (byte) 0x32, (byte) 0x31, (byte) 0x38, (byte) 0x66, (byte) 0x61, (byte) 0x34,
        (byte) 0x2D, (byte) 0x38, (byte) 0x30, (byte) 0x36, (byte) 0x38, (byte) 0x2D, (byte) 0x34, (byte) 0x65,
        (byte) 0x30, (byte) 0x38, (byte) 0x2D, (byte) 0x39, (byte) 0x34, (byte) 0x35, (byte) 0x39, (byte) 0x2D,
        (byte) 0x62, (byte) 0x64, (byte) 0x64, (byte) 0x62, (byte) 0x39, (byte) 0x63, (byte) 0x36, (byte) 0x39,
        (byte) 0x64, (byte) 0x31, (byte) 0x32, (byte) 0x65, (byte) 0x2D, (byte) 0x63, (byte) 0x31, (byte) 0x32,
        (byte) 0x61, (byte) 0x31, (byte) 0x62, (byte) 0x35, (byte) 0x32, (byte) 0x2D, (byte) 0x30, (byte) 0x39,
        (byte) 0x33, (byte) 0x38, (byte) 0x2D, (byte) 0x34, (byte) 0x34, (byte) 0x36, (byte) 0x33, (byte) 0x2D,
        (byte) 0x62, (byte) 0x66, (byte) 0x32, (byte) 0x34, (byte) 0x2D, (byte) 0x37, (byte) 0x65, (byte) 0x39,
        (byte) 0x65, (byte) 0x35, (byte) 0x31, (byte) 0x61, (byte) 0x62, (byte) 0x39, (byte) 0x37, (byte) 0x34,
        (byte) 0x63
    };

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

    /**
     * Algoritmo para generacion de firma digital del token.
     */
    private static final Algorithm TOKEN_SIGNING_ALGORITHM = Algorithm.HS256;

    /**
     * Codificador/Decodificador de tokens JWT.
     */
    private final JWTHandler<JsonObject> coder;
    //</editor-fold>

    /**
     * Constructor.
     */
    public JwtTokenManager() {
        this("---");
    }

    /**
     * Constructor.
     *
     * @param issuer Nombre de emisor de tokens.
     */
    public JwtTokenManager(String issuer) {
        this.coder = new JWTHandlerBuilder<JsonObject>()
                .withSecret(TOKEN_SIGNING_KEY)
                .withAlgorithm(TOKEN_SIGNING_ALGORITHM)
                .withDataClass(JsonObject.class)
                .withIssuer(issuer)
                .withJwtIdGenerator(JWTIdGenerator.UUID_JWT_ID)
                .build();
    }

    /**
     * Codifica un objeto JSON en un token JWT.
     *
     * @param payload Objeto JSON.
     * @return Token JWT.
     */
    @Override
    public String encodeToken(JsonObject payload) {
        try {
            JsonObject transformedPayload = encryptPayload(payload);
            return coder.encode(transformedPayload);
        } catch (Throwable thr) {
            return "";
        }
    }

    /**
     * Decodifica un token JWT y obtiene la carga util representada por un
     * objeto JSON.
     *
     * @param token Token JWT.
     * @return Objeto JSON.
     */
    @Override
    public JsonObject decodeToken(String token) {
        try {
            JsonObject transformedPayload = coder.decode(token);
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
