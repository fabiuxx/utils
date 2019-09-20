/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonObjectBuilder {

    private JsonObject json;

    private JsonObjectBuilder(JsonObject json) {
        this.json = json;
    }

    public static JsonObjectBuilder instance() {
        JsonObject json = new JsonObject();
        return new JsonObjectBuilder(json);
    }

    public JsonObjectBuilder clear() {
        this.json = new JsonObject();
        return this;
    }

    public JsonObjectBuilder add(String property, String value) {
        json.addProperty(property, value);
        return this;
    }

    public JsonObjectBuilder add(String property, Number value) {
        json.addProperty(property, value);
        return this;
    }

    public JsonObjectBuilder add(String property, Boolean value) {
        json.addProperty(property, value);
        return this;
    }

    public JsonObjectBuilder add(String property, Character value) {
        json.addProperty(property, value);
        return this;
    }

    public JsonObjectBuilder add(String property, JsonElement value) {
        json.add(property, value);
        return this;
    }

    public JsonObject build() {
        return json;
    }

}
