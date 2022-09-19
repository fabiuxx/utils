/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.adapter.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.json.adapter.JsonAdapterToJson;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Epoch2Json extends JsonAdapterToJson<Date> {

    @Override
    public Class<Date> getInputConversionType() {
        return Date.class;
    }

    @Override
    public JsonElement adapt(Date obj, Object... args) {
        Long epoch;
        if (obj == null) {
            epoch = -1L;
        } else {
            epoch = Fechas.toEpoch(obj);
        }
        return new JsonPrimitive(epoch);
    }

}
