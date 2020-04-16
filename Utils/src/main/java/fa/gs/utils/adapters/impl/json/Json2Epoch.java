/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters.impl.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.json.adapter.JsonAdapterFromJson;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Json2Epoch extends JsonAdapterFromJson<Date> {

    @Override
    public Class<Date> getOutputConversionType() {
        return Date.class;
    }

    @Override
    public Date adapt(JsonElement obj, Object... args) {
        if (obj == null || obj.isJsonNull()) {
            return null;
        }

        if (!obj.isJsonPrimitive()) {
            throw Errors.illegalArgument();
        }

        JsonPrimitive primitive = obj.getAsJsonPrimitive();
        if (!primitive.isNumber()) {
            throw Errors.illegalArgument();
        }

        Number value = primitive.getAsNumber();
        long epoch = value.longValue();
        return Fechas.fromEpoch(epoch);
    }

}
