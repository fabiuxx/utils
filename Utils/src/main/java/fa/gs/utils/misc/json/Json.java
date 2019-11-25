/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fa.gs.utils.adapters.Adapter;
import fa.gs.utils.adapters.Adapters;
import fa.gs.utils.adapters.impl.json.JsonArrayAdapter;
import fa.gs.utils.misc.Type;
import java.lang.reflect.Array;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Json {

    /**
     * Convierte una cadena de texto a un elemento JSON.
     *
     * @param text Cadena de texto.
     * @return Elemento JSON.
     */
    public static JsonElement parse(String text) {
        JsonParser parser = new JsonParser();
        return parser.parse(text);
    }

    /**
     * Convierte un elemento JSON a una cadena de texto.
     *
     * @param json Elemento JSON.
     * @return Cadena de texto.
     */
    public static String toString(JsonElement json) {
        return (json != null) ? json.toString() : "";
    }

    public static <T> T[] unwrapPrimitives(JsonArray array, Type type, Class<T> primitiveType) {
        if (array == null || array.size() == 0) {
            return null;
        }

        T[] primitives = (T[]) Array.newInstance(primitiveType, array.size());
        for (int i = 0; i < array.size(); i++) {
            try {
                T primitive = (T) JsonResolver.reduceValue(array.get(i), type);
                primitives[i] = primitive;
            } catch (Throwable thr) {
                primitives[i] = null;
            }
        }

        return primitives;
    }

    public static <T> JsonElement toArrayData(Class<? extends Adapter<T, JsonElement>> adapterClass, Collection<T> objs) {
        Collection<JsonElement> obj0 = Adapters.adapt(adapterClass, objs);
        JsonArrayBuilder array = JsonArrayBuilder.instance();
        array.add(obj0);
        return Adapters.adapt(JsonArrayAdapter.class, array.build());
    }

}
