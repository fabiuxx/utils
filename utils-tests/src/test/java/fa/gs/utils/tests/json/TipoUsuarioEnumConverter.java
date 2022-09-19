/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.json.adapter.JsonAdapterFromJson;

/**
 *
 * @author Fabio A. González Sosa
 */
public class TipoUsuarioEnumConverter extends JsonAdapterFromJson<TipoUsuario> {

    @Override
    public Class<TipoUsuario> getOutputConversionType() {
        return TipoUsuario.class;
    }

    @Override
    public TipoUsuario adapt(JsonElement obj, Object... args) {
        // Verifica que sea un valor primitivo, no un array ni otro objeto {}.
        if (obj.isJsonPrimitive()) {
            JsonPrimitive primitive = obj.getAsJsonPrimitive();
            // verifica que sea una cadena, no un numero ni booleano.
            if (primitive.isString()) {
                String codigo = primitive.getAsString();
                // Convierte la cadena a una enumeracion.
                return TipoUsuario.fromCodigo(codigo);
            }
        }

        throw Errors.illegalArgument();
    }

}
