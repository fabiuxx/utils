/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.api.responses;

import com.google.gson.JsonElement;
import fa.gs.utils.adapters.Adapter;
import fa.gs.utils.adapters.Adapters;
import fa.gs.utils.adapters.impl.json.JsonElementSliceAdapter;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.slices.JsonElementSlice;
import fa.gs.utils.collections.slices.Slices;
import fa.gs.utils.misc.json.JsonAdapter;
import fa.gs.utils.result.simple.Result;
import java.util.Collection;
import javax.ws.rs.core.Response;

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
