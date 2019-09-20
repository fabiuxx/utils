/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fa.gs.utils.adapters.Adapter;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.Type;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonAdapter {

    public static <T> Collection<JsonElement> adapt(Collection<T> objects, Adapter<T, JsonElement> adapter) {
        Collection<JsonElement> elements = Lists.empty();
        for (T object : objects) {
            JsonElement element = adapter.adapt(object);
            elements.add(element);
        }
        return elements;
    }

    public static Map<String, String> adapt(JsonElement element) {
        Map<String, String> map = Maps.empty();
        if (element.isJsonObject()) {
            JsonObject json = element.getAsJsonObject();
            for (Map.Entry<String, JsonElement> property : json.entrySet()) {
                try {
                    String name = property.getKey();
                    String value = JsonResolver.reduceValue(property.getValue(), Type.STRING, true, null);
                    map.put(name, value);
                } catch (Throwable thr) {
                    ;
                }
            }
        }
        return map;
    }

    public static JsonObject adapt(Map<String, String> map) {
        JsonObjectBuilder builder = JsonObjectBuilder.instance();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            builder.add(name, value);
        }

        return builder.build();
    }

    public static <T> T[] adaptPrimitives(JsonArray array, Type type, Class<T> primitiveType) {
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

    public static Integer[] integers(JsonArray array) {
        return adaptPrimitives(array, Type.INTEGER, Integer.class);
    }

}
