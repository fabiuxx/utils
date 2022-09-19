/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.controllers;

import com.google.gson.JsonElement;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.json.Json;
import fa.gs.utils.rest.exceptions.ApiBadRequestException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class RestControllerActionWithJsonElementParam implements RestControllerAction<String> {

    @Override
    public Response doAction(String text) throws Throwable {
        // Control de seguridad.
        if (text == null || text.isEmpty()) {
            throw new ApiBadRequestException();
        }

        // Parsear texto.
        JsonElement param;
        try {
            param = Json.fromString(text);
        } catch (Throwable thr) {
            Errors.dump(System.err, thr);
            param = null;
        }

        if (param == null) {
            throw new ApiBadRequestException();
        }

        // Ejecutar accion.
        return doAction(param);
    }

    public abstract Response doAction(JsonElement element) throws Throwable;

}
