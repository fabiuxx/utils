/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fa.gs.utils.misc.errors.Errors;
import javax.ws.rs.core.Response;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class RestControllerActionWithJsonObjectParam extends RestControllerActionWithJsonElementParam {

    @Override
    public Response doAction(JsonElement element) throws Throwable {
        if (!element.isJsonObject()) {
            throw Errors.illegalArgument("Se esperaba un objeto JSON.");
        }

        JsonObject json = element.getAsJsonObject();
        return doAction(json);
    }

    public abstract Response doAction(JsonObject element) throws Throwable;

}
