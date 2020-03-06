/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import com.google.gson.JsonElement;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.json.adapter.JsonAdapterFromJson;
import fa.gs.utils.misc.json.serialization.JsonDeserializer;
import fa.gs.utils.misc.json.serialization.JsonPostConstruct;
import fa.gs.utils.misc.json.serialization.JsonProperty;
import fa.gs.utils.misc.json.serialization.JsonResolution;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import lombok.ToString;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Json_Deserialize {

    @Test
    public void test0() throws Throwable {
        String json0 = "{\"enum1\": 1, \"enum2\": \"ABC123\", \"a\": 1, \"b\": 654.5, \"sub\": {\"x\": \"A\", \"y\": \"B\", \"bd\": 123123.123, \"bi\": 123123}, \"strings\": [1, 2, 3, 4]}";
        AB instance = JsonDeserializer.deserialize(json0, AB.class);
        System.out.println(instance);
        Assertions.assertNotNull(instance);
        Assertions.assertNotNull(instance.sub0);
        Assertions.assertEquals("A", instance.sub0.X);
        Assertions.assertEquals("B", instance.sub0.Y);
        Assertions.assertEquals(new BigDecimal("123123.123"), instance.sub0.bd);
        Assertions.assertEquals(new BigInteger("123123"), instance.sub0.bi);
        Assertions.assertNotNull(instance.enumOk);
        Assertions.assertEquals(EnumX.ABC123, instance.enumOk);
        Assertions.assertNotNull(instance.enumKo);
        Assertions.assertEquals(EnumX.DEF456, instance.enumKo);

        Assertions.assertNotNull(instance.strings);
        Assertions.assertTrue(instance.strings.size() > 0);
        for (Object obj : instance.strings) {
            System.out.println("ELEMENTO: " + String.valueOf(obj));
        }
    }

    @ToString
    private static class AB {

        @JsonProperty(name = "a")
        public Integer A = null;

        @JsonProperty
        public Integer b = null;

        @JsonProperty(name = "sub")
        public SUB sub0 = null;

        @JsonProperty(name = "enum1", fromJsonAdapter = EnumXConverter.class)
        public EnumX enumKo = null;

        @JsonProperty(name = "enum2", fromJsonAdapter = EnumXConverter.class)
        public EnumX enumOk = null;

        @JsonProperty(name = "strings")
        public Collection<Integer> strings = Lists.empty();

        @JsonPostConstruct
        void init() {
            System.out.println("POST CONSTRUCT!");
        }

    }

    @ToString
    private static class SUB {

        @JsonProperty(name = "x")
        public String X = null;

        @JsonProperty(name = "y")
        public String Y = null;

        @JsonProperty(name = "bd", resolution = JsonResolution.OPTIONAL)
        public BigDecimal bd;

        @JsonProperty(name = "bi")
        public BigInteger bi;

    }

    private static enum EnumX {
        ABC123,
        DEF456
    }

    private static class EnumXConverter extends JsonAdapterFromJson<EnumX> {

        @Override
        public Class<EnumX> getOutputConversionType() {
            return EnumX.class;
        }

        @Override
        public EnumX adapt(JsonElement input, Object... args) {
            String s = input.getAsString();
            if (s.equals("ABC123")) {
                return EnumX.ABC123;
            } else {
                return EnumX.DEF456;
            }
        }

    }
}
