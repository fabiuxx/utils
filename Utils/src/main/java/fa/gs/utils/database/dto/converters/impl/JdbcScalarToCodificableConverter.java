/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.errors.Errors;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JdbcScalarToCodificableConverter extends AbstractJdbcScalarConverter<Codificable> {

    @Getter
    @Setter
    private Class<?> enumCodificableClass;

    @Override
    protected Codificable nullSafeConvert(Object value) {
        if (enumCodificableClass == null) {
            throw Errors.illegalState("No se establecio el tipo de enumeracion para la conversion.");
        }

        if (value instanceof String) {
            return Codificable.fromCodigo((String) value, (Codificable[]) enumCodificableClass.getEnumConstants());
        }

        throw unsupportedType(value);
    }

}
