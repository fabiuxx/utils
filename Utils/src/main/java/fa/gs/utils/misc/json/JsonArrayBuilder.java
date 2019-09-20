/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonArrayBuilder {

    private JsonArray array;

    private JsonArrayBuilder(JsonArray array) {
        this.array = array;
    }

    public static JsonArrayBuilder instance() {
        JsonArray array = new JsonArray();
        return new JsonArrayBuilder(array);
    }

    public JsonArrayBuilder clear() {
        this.array = new JsonArray();
        return this;
    }

    public JsonArrayBuilder add(String value) {
        array.add(value);
        return this;
    }

    public JsonArrayBuilder add(Number value) {
        array.add(value);
        return this;
    }

    public JsonArrayBuilder add(Boolean value) {
        array.add(value);
        return this;
    }

    public JsonArrayBuilder add(Character value) {
        array.add(value);
        return this;
    }

    public JsonArrayBuilder add(JsonElement value) {
        array.add(value);
        return this;
    }

    public JsonArrayBuilder add(Collection<JsonElement> values) {
        for (JsonElement value : values) {
            if (value != null) {
                array.add(value);
            }
        }
        return this;
    }

    public JsonArray build() {
        return array;
    }
}
