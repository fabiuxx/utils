/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import fa.gs.utils.misc.fechas.Fechas;
import java.util.Date;

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
        if (value != null) {
            json.addProperty(property, value);
        } else {
            json.add(property, JsonNull.INSTANCE);
        }
        return this;
    }

    public JsonObjectBuilder add(String property, Number value) {
        if (value != null) {
            json.addProperty(property, value);
        } else {
            json.add(property, JsonNull.INSTANCE);
        }
        return this;
    }

    public JsonObjectBuilder add(String property, Boolean value) {
        if (value != null) {
            json.addProperty(property, value);
        } else {
            json.add(property, JsonNull.INSTANCE);
        }
        return this;
    }

    public JsonObjectBuilder add(String property, Character value) {
        if (value != null) {
            json.addProperty(property, value);
        } else {
            json.add(property, JsonNull.INSTANCE);
        }
        return this;
    }

    public JsonObjectBuilder add(String property, Date value) {
        if (value != null) {
            json.addProperty(property, Fechas.toEpoch(value));
        } else {
            json.add(property, JsonNull.INSTANCE);
        }

        return this;
    }

    public JsonObjectBuilder add(String property, JsonElement value) {
        if (value != null) {
            json.add(property, value);
        } else {
            json.add(property, JsonNull.INSTANCE);
        }
        return this;
    }

    public JsonObject build() {
        return json;
    }

}
