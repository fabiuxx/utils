/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.adapter;

import com.google.gson.JsonElement;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class JsonAdapterToJson<T> implements JsonAdapter<T, JsonElement> {

    @Override
    public Class<JsonElement> getOutputConversionType() {
        return JsonElement.class;
    }

}
