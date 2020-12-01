/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.adapter.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.json.adapter.JsonAdapterToJson;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Codificable2Json extends JsonAdapterToJson<Codificable> {

    @Override
    public Class<Codificable> getInputConversionType() {
        return Codificable.class;
    }

    @Override
    public JsonElement adapt(Codificable obj, Object... args) {
        if (obj == null) {
            return JsonNull.INSTANCE;
        } else {
            return new JsonPrimitive(obj.codigo());
        }
    }

}
