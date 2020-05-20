/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class SimpleJdbcScalarCastConverter<T> extends AbstractJdbcScalarConverter<T> {

    private final Class<T> domainClass;

    protected SimpleJdbcScalarCastConverter(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    @Override
    protected T nullSafeConvert(Object value) {
        return domainClass.cast(value);
    }

}
