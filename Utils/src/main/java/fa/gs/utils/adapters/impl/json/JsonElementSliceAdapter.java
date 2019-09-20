/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters.impl.json;

import com.google.gson.JsonElement;
import fa.gs.utils.adapters.impl.Adapter0;
import fa.gs.utils.collections.slices.JsonElementSlice;
import fa.gs.utils.misc.json.JsonArrayBuilder;
import fa.gs.utils.misc.json.JsonObjectBuilder;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonElementSliceAdapter extends Adapter0<JsonElementSlice, JsonElement> {

    @Override
    public JsonElement adapt(JsonElementSlice obj) {
        JsonArrayBuilder data = JsonArrayBuilder.instance();
        data.add(obj.getElements());

        JsonObjectBuilder builder = JsonObjectBuilder.instance();
        builder.add("data", data.build());
        builder.add("subtotal", obj.getSubtotal());
        builder.add("total", obj.getTotal());
        return builder.build();
    }

}
