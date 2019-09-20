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
import fa.gs.utils.misc.Type;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    public static JsonElement parse(String text) {
        JsonParser parser = new JsonParser();
        return parser.parse(text);
    }

    /**
     * Obtiene una propiedad de fecha desde un objeto json.
     *
     * @param json Objeto json.
     * @param path Camino para la resolucion de la propiedad dentro del json.
     * @return Objeto fecha que contiene el valor indicado por la propiedad en
     * el objeto json, si hubiere. Caso contrario {@code null}.
     */
    public static Date getTimestampProperty(JsonObject json, String path) {
        return JsonResolver.get(json, path, Type.EPOCH);
    }

    /**
     * Adapta un array json de elementos a una coleccion especifica de objetos
     * json.
     *
     * @param array Array json.
     * @return Lista de objetos json.
     */
    public static List<JsonObject> adapt(JsonArray array) {
        List<JsonObject> list = new LinkedList<>();
        Iterator<JsonElement> it = array.iterator();
        while (it.hasNext()) {
            JsonElement element = it.next();
            if (element != null) {
                list.add(element.getAsJsonObject());
            }
        }
        return list;
    }

    /**
     * Adapta un mapa de elementos (del mismo tipo) a un objeto JSON.
     *
     * @param <T> Parametro de tipo para valores en mapa.
     * @param map Mapa de valores.
     * @return Objeto JSON.
     */
    public static <T extends Object> JsonElement adapt(Map<String, T> map) {
        // Control de seguridad.
        if (map == null) {
            return null;
        }

        return adapt(map.entrySet());
    }

    /**
     * Adapta una coleccion de pares (clave, valor) a un objeto JSON.
     *
     * @param <T> Parametro de tipo para valores en mapa.
     * @param entries Pares de (clave, valor).
     * @return Objeto JSON.
     */
    public static <T extends Object> JsonElement adapt(Set<Map.Entry<String, T>> entries) {
        // Control de seguridad.
        if (entries == null) {
            return null;
        }

        JsonArray array = new JsonArray();
        for (Map.Entry<String, T> entry : entries) {
            JsonObject pair = new JsonObject();
            pair.addProperty("nombre", entry.getKey());
            pair.addProperty("valor", String.valueOf(entry.getValue()));
            array.add(pair);
        }

        JsonObject json = new JsonObject();
        json.add("data", array);
        json.addProperty("total", array.size());
        return json;
    }

    /**
     * Adapta un objeto que encapsula un error a un objeto JSON, y todos los
     * errores subyacentes que pudieran existir como causa del mismo.
     *
     * @param thr Error capturado.
     * @return Objeto JSON.
     */
    public static JsonElement adapt(Throwable thr) {
        // Control de seguridad.
        if (thr == null) {
            return null;
        }

        JsonObject json = new JsonObject();
        try {
            JsonObject parent = null;
            JsonObject child = null;
            parent = json = adaptThrowable(thr);
            while ((thr = thr.getCause()) != null) {
                child = adaptThrowable(thr);
                parent.add("cause", child);
                parent = child;
            }
        } catch (Throwable thr2) {
            ;
        }
        return json;
    }

    /**
     * Adapta un objeto que encapsula un error a un objeto JSON.
     *
     * @param thr Error capturado.
     * @return Objeto JSON.
     */
    private static JsonObject adaptThrowable(Throwable thr) {
        JsonObject json = new JsonObject();
        json.addProperty("mensaje", thr.getMessage());
        json.addProperty("mensaje_localizado", thr.getLocalizedMessage());
        json.addProperty("clase", thr.getClass().getCanonicalName());
        json.add("trace", Json.adapt(thr.getStackTrace()));
        return json;
    }

    /**
     * Adapta un array de elementos que representan entradas en la pila de
     * llamadas a metodos.
     *
     * @param stackTrace Array de entradas de pila de llamadas.
     * @return Objeto JSON.
     */
    public static JsonElement adapt(StackTraceElement[] stackTrace) {
        return Json.adapt(stackTrace, null);
    }

    /**
     * Adapta un array de elementos que representan entradas en la pila de
     * llamadas a metodos.
     *
     * @param stackTrace Array de entradas de pila de llamadas.
     * @param packageName Nombre de paquete que sirve como filtro para descartar
     * entradas que no concuerden con este parametro.
     * @return Objeto JSON.
     */
    public static JsonElement adapt(StackTraceElement[] stackTrace, String packageName) {
        int count = 0;
        JsonArray array = new JsonArray();
        for (StackTraceElement element : stackTrace) {
            if (packageName == null || String.valueOf(element.getClassName()).startsWith(packageName)) {
                count++;
                JsonElement json = Json.adapt(element);
                array.add(json);
            }
        }

        JsonObject json = new JsonObject();
        json.addProperty("total", count);
        json.add("data", array);
        return json;
    }

    /**
     * Adapta un elemento que representa a una entrada en la pila de llamadas a
     * metodos.
     *
     * @param stackTraceElement Entradas de pila de llamadas.
     * @return Objeto JSON.
     */
    public static JsonElement adapt(StackTraceElement stackTraceElement) {
        JsonObject json = new JsonObject();
        json.addProperty("clase", String.valueOf(stackTraceElement.getClassName()));
        json.addProperty("llamador", String.valueOf(stackTraceElement.getMethodName()));
        json.addProperty("archivo", String.valueOf(stackTraceElement.getFileName()));
        json.addProperty("linea", String.valueOf(stackTraceElement.getLineNumber()));
        return json;
    }

}
