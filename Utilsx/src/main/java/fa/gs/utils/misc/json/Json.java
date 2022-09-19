/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map.Entry;
import java.util.Set;

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
    public static JsonElement fromString(String text) {
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

    /**
     * Obtiene una propiedad dentro de un objeto json.
     *
     * @param json Objeto json.
     * @param path Camino hasta la propiedad deseada.
     * @return Un elemento json que contiene el valor de la propiedad buscada,
     * caso contrario {@code null}.
     */
    public static JsonElement resolvePath(JsonObject json, String path) {
        String[] seg = path.split("\\.");
        JsonElement ele = json;
        for (int i = 0; i < seg.length; i++) {
            String element = seg[i];
            if (json.has(element)) {
                ele = json.get(element);
                if (ele == null) {
                    return null;
                } else if (ele.isJsonObject()) {
                    json = ele.getAsJsonObject();
                } else if (ele.isJsonObject() == false && i < (seg.length - 1)) {
                    return null;
                } else if (ele.isJsonObject() == false && i == (seg.length - 1)) {
                    return ele;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return ele;
    }

    /**
     * Determina si una propiedad es accesible dentro de un objeto json.
     *
     * @param json Objeto json.
     * @param path Camino hasta la propiedad deseada.
     * @return {@code true} si el camino es accesible, caso contrario
     * {@code false}.
     */
    public static boolean hasPath(JsonObject json, String path) {
        JsonElement element = resolvePath(json, path);
        return (element != null);
    }

    /**
     * <p>
     * Determinar si dos elementos JSON son iguales.
     * </p>
     * <p>
     * Fuente: http://blog.sodhanalibrary.com/2016/02/compare-json-with-java-using-google.html#.Ysh9ynbMKUk
     * </p>
     *
     * @param json1 Elemento 1.
     * @param json2 Elemento 2.
     * @return {@code true} si ambos elementos son equivalentes, caso contrario
     * {@code false}.
     */
    public static boolean equals(JsonElement json1, JsonElement json2) {
        boolean isEqual = true;
        // Check whether both jsonElement are not null
        if (json1 != null && json2 != null) {
            // Check whether both jsonElement are objects
            if (json1.isJsonObject() && json2.isJsonObject()) {
                Set<Entry<String, JsonElement>> ens1 = ((JsonObject) json1).entrySet();
                Set<Entry<String, JsonElement>> ens2 = ((JsonObject) json2).entrySet();
                JsonObject json2obj = (JsonObject) json2;
                if (ens1 != null && ens2 != null && (ens2.size() == ens1.size())) {
                    // Iterate JSON Elements with Key values
                    for (Entry<String, JsonElement> en : ens1) {
                        isEqual = isEqual && equals(en.getValue(), json2obj.get(en.getKey()));
                    }
                } else {
                    return false;
                }
            } // Check whether both jsonElement are arrays
            else if (json1.isJsonArray() && json2.isJsonArray()) {
                JsonArray jarr1 = json1.getAsJsonArray();
                JsonArray jarr2 = json2.getAsJsonArray();
                if (jarr1.size() != jarr2.size()) {
                    return false;
                } else {
                    int i = 0;
                    // Iterate JSON Array to JSON Elements
                    for (JsonElement je : jarr1) {
                        isEqual = isEqual && equals(je, jarr2.get(i));
                        i++;
                    }
                }
            } // Check whether both jsonElement are null
            else if (json1.isJsonNull() && json2.isJsonNull()) {
                return true;
            } // Check whether both jsonElement are primitives
            else if (json1.isJsonPrimitive() && json2.isJsonPrimitive()) {
                if (json1.equals(json2)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (json1 == null && json2 == null) {
            return true;
        } else {
            return false;
        }
        return isEqual;
    }

}
