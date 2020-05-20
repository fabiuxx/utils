/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.database.dto.converters.DtoProjectionConverter;
import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.errors.Errors;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class AbstractCodificableProjectionConverter<T extends Codificable> extends DtoProjectionConverter<T> {

    @Override
    public T convertProjection(Object projection) {
        if (projection == null) {
            return null;
        }

        if (projection instanceof String) {
            String codigo = (String) projection;
            return convertCodificable(codigo);
        }

        throw Errors.illegalArgument("Tipo '%s' no soportado.", projection.getClass().getCanonicalName());
    }

    protected abstract T convertCodificable(String codigo);

}
