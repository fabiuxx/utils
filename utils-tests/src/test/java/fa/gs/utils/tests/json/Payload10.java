/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.json;

import com.google.gson.JsonElement;
import fa.gs.utils.misc.json.serialization.JsonProperty;
import java.io.Serializable;
import java.math.BigInteger;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class Payload10 implements Serializable {

    @JsonProperty
    public BigInteger id;

    @JsonProperty
    public Payload10_0[] items;

    @Data
    public static class Payload10_0 implements Serializable {

        @JsonProperty
        public BigInteger id;

        @JsonProperty
        public JsonElement data;
    }

}
