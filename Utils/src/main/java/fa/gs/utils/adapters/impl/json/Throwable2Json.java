/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters.impl.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.json.JsonArrayBuilder;
import fa.gs.utils.misc.json.JsonObjectBuilder;
import fa.gs.utils.misc.json.adapter.JsonAdapterToJson;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Throwable2Json extends JsonAdapterToJson<Throwable> {

    @Override
    public Class<Throwable> getInputConversionType() {
        return Throwable.class;
    }

    @Override
    public JsonElement adapt(Throwable obj, Object... args) {
        JsonObject json = new JsonObject();

        try {
            JsonObject parent = null;
            JsonObject child = null;
            parent = json = adaptThrowable(obj);
            while ((obj = obj.getCause()) != null) {
                child = adaptThrowable(obj);
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
    private JsonObject adaptThrowable(Throwable thr) {
        JsonObjectBuilder json = JsonObjectBuilder.instance();
        json.add("mensaje", thr.getMessage());
        json.add("mensaje_localizado", thr.getLocalizedMessage());
        json.add("clase", thr.getClass().getCanonicalName());
        json.add("trace", adapt(thr.getStackTrace()));
        return json.build();
    }

    /**
     * Adapta un array de elementos que representan entradas en la pila de
     * llamadas a metodos.
     *
     * @param stackTrace Array de entradas de pila de llamadas.
     * @return Objeto JSON.
     */
    public JsonElement adapt(StackTraceElement[] stackTrace) {
        JsonArrayBuilder arrayBuilder = JsonArrayBuilder.instance();
        if (!Assertions.isNullOrEmpty(stackTrace)) {
            for (StackTraceElement element : stackTrace) {
                JsonElement json = adapt(element);
                arrayBuilder.add(json);
            }
        }

        JsonArray array = arrayBuilder.build();

        JsonObjectBuilder builder = JsonObjectBuilder.instance();
        builder.add("total", array.size());
        builder.add("data", array);
        return builder.build();
    }

    /**
     * Adapta un elemento que representa a una entrada en la pila de llamadas a
     * metodos.
     *
     * @param stackTraceElement Entradas de pila de llamadas.
     * @return Objeto JSON.
     */
    public JsonElement adapt(StackTraceElement stackTraceElement) {
        JsonObjectBuilder json = JsonObjectBuilder.instance();
        json.add("clase", stackTraceElement.getClassName());
        json.add("llamador", stackTraceElement.getMethodName());
        json.add("archivo", stackTraceElement.getFileName());
        json.add("linea", stackTraceElement.getLineNumber());
        return json.build();
    }

}
