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
public abstract class JsonAdapterFromJson<T> implements JsonAdapter<JsonElement, T> {

    @Override
    public Class<JsonElement> getInputConversionType() {
        return JsonElement.class;
    }

}
