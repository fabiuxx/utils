/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappers;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.maps.ResultSetMap;
import fa.gs.utils.database.utils.ResultSetMapper;
import fa.gs.utils.mixins.Self;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 * @param <U> Parametro de tipo.
 */
public abstract class PojoMapper<T extends PojoMapper<T, U>, U> extends ResultSetMapper<U> implements Self<T> {

    private final Collection<AttributeMapper> mappers;

    public PojoMapper() {
        this.mappers = Lists.empty();
    }

    public T with(AttributeMapper mapper) {
        if (mapper != null) {
            this.mappers.add(mapper);
        }

        return self();
    }

    @Override
    protected U adapt(U adaptee, ResultSetMap resultSet) {
        for (AttributeMapper mapper : mappers) {
            mapper.map(adaptee, resultSet.getMap());
        }

        return adaptee;
    }

}
