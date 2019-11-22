/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters.impl.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fa.gs.utils.adapters.impl.Adapter0;
import fa.gs.utils.misc.json.JsonObjectBuilder;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonArrayAdapter extends Adapter0<JsonArray, JsonObject> {

    @Override
    public JsonObject adapt(JsonArray obj) {
        JsonObjectBuilder builder = JsonObjectBuilder.instance();
        builder.add("total", obj.size());
        builder.add("data", obj);
        return builder.build();
    }

}
