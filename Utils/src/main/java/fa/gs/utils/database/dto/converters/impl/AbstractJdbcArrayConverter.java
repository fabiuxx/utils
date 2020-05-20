/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.database.dto.converters.DtoProjectionConverter;
import fa.gs.utils.database.dto.mapping.NativeJdbcResultSetAdapter.JdbcArray;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class AbstractJdbcArrayConverter<T> extends DtoProjectionConverter<T> {

    @Override
    public T convertProjection(Object projection) {
        if (projection == null) {
            return null;
        }

        if (projection instanceof JdbcArray) {
            JdbcArray array = (JdbcArray) projection;
            Object[] values = array.getValue();
            if (Assertions.isNullOrEmpty(values)) {
                return null;
            }

            return nullSafeConvert(values);
        }

        throw Errors.illegalArgument("Tipo '%s' no soportado.", projection.getClass().getCanonicalName());
    }

    protected abstract T nullSafeConvert(Object[] values);

}
