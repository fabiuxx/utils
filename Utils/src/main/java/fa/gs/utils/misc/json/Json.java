/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

}
