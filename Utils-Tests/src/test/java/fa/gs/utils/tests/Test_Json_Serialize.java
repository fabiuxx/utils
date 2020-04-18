/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Numeric;
import fa.gs.utils.misc.json.adapter.JsonAdapterToJson;
import fa.gs.utils.misc.json.serialization.JsonPostConstruct;
import fa.gs.utils.misc.json.serialization.JsonProperty;
import fa.gs.utils.misc.json.serialization.JsonResolution;
import fa.gs.utils.misc.json.serialization.JsonSerializer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Json_Serialize {

    @Test
    public void test0() throws Throwable {
        SUB sub = new SUB();
        sub.X = "x";
        sub.bd = Numeric.wrap("123.456");

        AB instance = new AB();
        instance.A = 1;
        instance.b = 2;
        instance.sub0 = sub;
        instance.enum1 = EnumX.ABC123;
        instance.enum2 = EnumX.DEF456;
        instance.strings = Arrays.asList("A", "B");
        JsonElement json = JsonSerializer.serialize(instance);
        Assertions.assertNotNull(json);
        System.out.println(json.toString());
    }

    @ToString
    private static class AB {

        @JsonProperty(name = "a")
        public Integer A = null;

        @JsonProperty
        public Integer b = null;

        @JsonProperty(name = "sub")
        public SUB sub0 = null;

        @JsonProperty(name = "enum1", toJsonAdapter = EnumXConverter.class)
        public EnumX enum1 = null;

        @JsonProperty(toJsonAdapter = EnumXConverter.class)
        public EnumX enum2 = EnumX.DEF456;

        @JsonProperty(name = "strings")
        public Collection<String> strings = Lists.empty();

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

    private static class EnumXConverter extends JsonAdapterToJson<EnumX> {

        @Override
        public Class<EnumX> getInputConversionType() {
            return EnumX.class;
        }

        @Override
        public JsonElement adapt(EnumX obj, Object... args) {
            if (obj == null) {
                return JsonNull.INSTANCE;
            }

            if (obj == EnumX.ABC123) {
                return new JsonPrimitive("A1");
            } else {
                return new JsonPrimitive("D4");
            }
        }

    }

    private static class IntegerConverter extends JsonAdapterToJson<Integer> {

        @Override
        public Class<Integer> getInputConversionType() {
            return Integer.class;
        }

        @Override
        public JsonElement adapt(Integer obj, Object... args) {
            if (obj == null) {
                return JsonNull.INSTANCE;
            }

            obj = obj * 1000;
            return new JsonPrimitive(obj);
        }

    }
}
