/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fa.gs.utils.misc.errors.Errno;

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
            errno = ServiceResponse.BASE_RESPONSE_ERRNO;
        }

        JsonObject status = new JsonObject();
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
