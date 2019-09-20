/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.api.responses;

import com.google.gson.JsonElement;
import java.util.Collection;
import javax.ws.rs.core.Response;
import py.com.generica.utils.adapters.Adapter;
import py.com.generica.utils.adapters.Adapters;
import py.com.generica.utils.adapters.impl.json.JsonElementSliceAdapter;
import py.com.generica.utils.collections.Lists;
import py.com.generica.utils.collections.slices.JsonElementSlice;
import py.com.generica.utils.collections.slices.Slices;
import py.com.generica.utils.misc.json.JsonAdapter;
import py.com.generica.utils.result.simple.Result;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ServiceResponse2 {

    /**
     * Permite convertir una coleccion de objetos a una respuesta completa que
     * representa el slice de objetos.
     *
     * @param <T> Parametro de tipo para objetos en la coleccion.
     * @param result Resultado de operacion de obtencion de coleccion de
     * objetos.
     * @param adapterClass Clase de adaptador de objetos.
     * @return Respuesta de servicio.
     */
    public static <T> Response responseJsonElementSlice(Result<Collection<T>> result, Class<? extends Adapter<T, JsonElement>> adapterClass) {
        if (result.isFailure()) {
            return ServiceResponse.ko().cause(result).build();
        } else {
            Collection<T> empty = Lists.empty();
            Collection<T> collection = result.value(empty);
            Collection<JsonElement> elements = JsonAdapter.adapt(collection, Adapters.instantiate(adapterClass));
            JsonElementSlice slice = Slices.jsonElements(elements);
            JsonElement json = Adapters.adapt(JsonElementSliceAdapter.class, slice);
            return ServiceResponse.ok().payload(json).build();
        }
    }

}
