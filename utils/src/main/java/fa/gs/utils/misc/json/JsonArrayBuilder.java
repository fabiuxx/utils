/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import fa.gs.utils.adapters.Adapter;
import fa.gs.utils.adapters.Adapters;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.fechas.Fechas;
import java.util.Collection;
import java.util.Date;

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
        return instance(array);
    }

    public static JsonArrayBuilder instance(JsonArray array) {
        return new JsonArrayBuilder(array);
    }

    public static <T> JsonArray adaptArray(Collection<T> array, Class<? extends Adapter<T, JsonElement>> elementAdapter) {
        JsonArrayBuilder ab = JsonArrayBuilder.instance();

        // Control.
        if (Assertions.isNullOrEmpty(array)) {
            return ab.build();
        }

        // Rellenar objeto.
        int i = 1;
        for (T element : array) {
            JsonElement jelement = Adapters.adapt(elementAdapter, element);
            ab.add(jelement);
            i++;
        }

        // Datos finales.
        return ab.build();
    }

    public JsonArrayBuilder clear() {
        this.array = new JsonArray();
        return this;
    }

    public JsonArrayBuilder add(String value) {
        if (value != null) {
            array.add(value);
        }
        return this;
    }

    public JsonArrayBuilder add(Number value) {
        if (value != null) {
            array.add(value);
        }
        return this;
    }

    public JsonArrayBuilder add(Boolean value) {
        if (value != null) {
            array.add(value);
        }
        return this;
    }

    public JsonArrayBuilder add(Character value) {
        if (value != null) {
            array.add(value);
        }
        return this;
    }

    public JsonArrayBuilder add(Date value) {
        if (value != null) {
            array.add(Fechas.toEpoch(value));
        }

        return this;
    }

    public JsonArrayBuilder add(JsonElement value) {
        if (value != null) {
            array.add(value);
        }
        return this;
    }

    public JsonArrayBuilder add(Collection<JsonElement> values) {
        for (JsonElement value : values) {
            add(value);
        }
        return this;
    }

    public JsonArray build() {
        return array;
    }

}
