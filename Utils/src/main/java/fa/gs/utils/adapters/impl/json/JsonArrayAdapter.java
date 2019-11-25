/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters.impl.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import fa.gs.utils.misc.json.JsonObjectBuilder;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonArrayAdapter extends ToJsonAdapter<JsonArray> {

    @Override
    protected JsonElement adapt0(JsonArray obj) {
        JsonObjectBuilder builder = JsonObjectBuilder.instance();
        builder.add("total", obj.size());
        builder.add("data", obj);
        return builder.build();
    }

}
