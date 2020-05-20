/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.collections.Arrays;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class SimpleJdbcArrayUnwrapConverter<T> extends AbstractJdbcArrayConverter<T[]> {

    private final Class<T> domainClass;

    protected SimpleJdbcArrayUnwrapConverter(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    @Override
    protected T[] nullSafeConvert(Object[] values) {
        return Arrays.unwrap(values, domainClass);
    }

}
