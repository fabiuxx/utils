/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.adapter.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import fa.gs.utils.misc.json.JsonElementSerializableWrapper;
import fa.gs.utils.misc.json.adapter.JsonAdapterToJson;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonElementSerializableWrapper2Json extends JsonAdapterToJson<JsonElementSerializableWrapper> {

    @Override
    public Class<JsonElementSerializableWrapper> getInputConversionType() {
        return JsonElementSerializableWrapper.class;
    }

    @Override
    public JsonElement adapt(JsonElementSerializableWrapper obj, Object... args) {
        if (obj == null || obj.getJsonElement() == null) {
            return JsonNull.INSTANCE;
        } else {
            return obj.getJsonElement();
        }
    }

}
