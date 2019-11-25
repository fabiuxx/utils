/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters.impl.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import fa.gs.utils.adapters.impl.Adapter0;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class ToJsonAdapter<TFrom> extends Adapter0<TFrom, JsonElement> {

    @Override
    public JsonElement adapt(TFrom obj) {
        if(obj == null) {
            return JsonNull.INSTANCE;
        } else {
            return adapt0(obj);
        }
    }
    
    protected abstract JsonElement adapt0(TFrom obj);
    
}
