/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.database.dto.converters.DtoProjectionConverter;
import fa.gs.utils.database.dto.mapping.NativeJdbcResultSetAdapter.JdbcScalar;
import fa.gs.utils.misc.errors.Errors;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class AbstractJdbcScalarConverter<T> extends DtoProjectionConverter<T> {

    @Override
    public T convertProjection(Object projection) {
        if (projection == null) {
            return null;
        }

        if (projection instanceof JdbcScalar) {
            JdbcScalar scalar = (JdbcScalar) projection;
            Object value0 = scalar.getValue();
            if (value0 == null) {
                return null;
            }

            return nullSafeConvert(value0);
        }

        throw unsupportedType(projection);
    }

    protected abstract T nullSafeConvert(Object value);

    protected IllegalArgumentException unsupportedType(Object value) {
        return Errors.illegalArgument("Tipo '%s' no soportado.", value.getClass().getCanonicalName());
    }

}
