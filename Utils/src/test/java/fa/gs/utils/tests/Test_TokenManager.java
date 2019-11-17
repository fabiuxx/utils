/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import com.google.gson.JsonObject;
import fa.gs.utils.authentication.tokens.TokenDecoder;
import fa.gs.utils.authentication.tokens.jwt.JwtTokenManager;
import fa.gs.utils.misc.json.JsonObjectBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_TokenManager {

    private static final String TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiItLS0iLCJkYXRhNGoiOiJ7XCJ2ZXJzaW9uXCI6XCIxXCIsXCJkYXRhXCI6XCJUSExDOGFoSGFNaXRiUTU3QWVwNC93XCJ9IiwianRpIjoiNzc3N2U1MDItOTNkYy00NWQ4LWIwNmQtZTQyM2ZjYTFmOThiIn0=.aQNrT/okRDnn4RWxk9G8B6HQnars12dbFolyFS+TaDI=";

    @Test
    public void test0() {
        JsonObjectBuilder builder = JsonObjectBuilder.instance();
        builder.add("a", 1);
        builder.add("b", 2);
        JsonObject json = builder.build();

        TokenDecoder<JsonObject> decoder = new JwtTokenManager();
        JsonObject json0 = decoder.decodeToken(TEST_TOKEN);
        Assert.assertTrue(json.equals(json0));
    }

}
