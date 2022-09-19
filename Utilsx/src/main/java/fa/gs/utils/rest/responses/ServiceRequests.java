/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.responses;

import com.google.gson.JsonElement;
import fa.gs.utils.misc.json.Json;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ServiceRequests {

    public static Entity writeJsonContent(JsonElement json) {
        Entity entity = Entity.entity(json.toString(), MediaType.APPLICATION_JSON);
        return entity;
    }

    public static JsonElement readJsonContent(Response response) {
        String json = response.readEntity(String.class);
        return Json.fromString(json);
    }

    public static String readTextContent(Response response) {
        String text = response.readEntity(String.class);
        return text;
    }

}
