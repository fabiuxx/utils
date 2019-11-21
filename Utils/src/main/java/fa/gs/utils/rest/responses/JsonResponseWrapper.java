/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fa.gs.utils.misc.errors.Errno;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.fechas.Fechas;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonResponseWrapper {

    /**
     * Encapsula un objeto JSON dentro de un mensaje de respuesta valido.
     *
     * @param raw Objeto JSON que compone la carga util de la respuesta.
     * @return Respuesta de exito.
     */
    public static JsonObject success(JsonElement raw) {
        JsonObject status = new JsonObject();
        status.addProperty("success", true);
        status.addProperty("timestamp", Fechas.epoch());
        JsonObject json = new JsonObject();
        json.add("status", status);
        if (raw != null) {
            json.add("payload", raw);
        }
        return json;
    }

    /**
     * Encapsula un objeto JSON dentro de un mensaje de respuesta fallida.
     *
     * @param raw Objeto JSON que compone la carga util de la respuesta.
     * @return Respuesta de fallo.
     */
    public static JsonObject failure(JsonElement raw) {
        return failure(raw, "Error Interno", null);
    }

    /**
     * Encapsula un objeto JSON dentro de un mensaje de respuesta fallida.
     *
     * @param raw Objeto JSON que compone la carga util de la respuesta.
     * @param cause Causa del fallo.
     * @return Respuesta de fallo.
     */
    public static JsonObject failure(JsonElement raw, Throwable cause) {
        return failure(raw, cause.getLocalizedMessage(), null);
    }

    /**
     * Encapsula un objeto JSON dentro de un mensaje de respuesta fallida.
     *
     * @param raw Objeto JSON que compone la carga util de la respuesta.
     * @param errno Codigo numerico que representa el fallo puntual producido.
     * @return Respuesta de fallo.
     */
    public static JsonObject failure(JsonElement raw, Errno errno) {
        return failure(raw, "Error Interno", errno);
    }

    /**
     * Encapsula un objeto JSON dentro de un mensaje de respuesta fallida.
     *
     * @param raw Objeto JSON que compone la carga util de la respuesta.
     * @param cause Causa del fallo.
     * @return Respuesta de fallo.
     */
    public static JsonObject failure(JsonElement raw, String cause) {
        return failure(raw, cause, null);
    }

    /**
     * Encapsula un objeto JSON dentro de un mensaje de respuesta fallida.
     *
     * @param raw Objeto JSON que compone la carga util de la respuesta.
     * @param cause Causa del fallo.
     * @param errno Codigo numerico que representa el fallo puntual producido.
     * @return Respuesta de fallo.
     */
    public static JsonObject failure(JsonElement raw, Throwable cause, Errno errno) {
        return failure(raw, cause.getLocalizedMessage(), errno);
    }

    /**
     * Encapsula un objeto JSON dentro de un mensaje de respuesta fallida.
     *
     * @param raw Objeto JSON que compone la carga util de la respuesta.
     * @param cause Causa del fallo.
     * @param errno Codigo numerico que representa el fallo puntual producido.
     * @return Respuesta de fallo.
     */
    public static JsonObject failure(JsonElement raw, String cause, Errno errno) {
        // Control de seguridad.
        if (errno == null) {
            errno = Errors.errno("API", 0);
        }

        JsonObject status = new JsonObject();
        status.addProperty("timestamp", Fechas.epoch());
        status.addProperty("success", false);
        status.addProperty("cause", cause);
        status.addProperty("errno", errno.getErrnoString());
        JsonObject json = new JsonObject();
        json.add("status", status);
        if (raw != null) {
            json.add("payload", raw);
        }
        return json;
    }

}
