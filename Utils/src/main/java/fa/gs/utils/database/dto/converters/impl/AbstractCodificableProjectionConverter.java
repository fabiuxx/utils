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
    public T convertProjection(Object value) {
        if (value == null) {
            throw Errors.illegalArgument();
        }

        if (!(value instanceof String)) {
            throw Errors.illegalArgument();
        }

        String codigo = (String) value;
        return convertCodificable(codigo);
    }

    protected abstract T convertCodificable(String codigo);

}
