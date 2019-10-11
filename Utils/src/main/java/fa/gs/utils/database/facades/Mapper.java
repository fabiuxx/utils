/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.facades;

import fa.gs.utils.adapters.impl.Adapter0;
import fa.gs.utils.collections.maps.ResultSetMap;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class Mapper<T> extends Adapter0<ResultSetMap, T> implements Serializable {

    protected abstract T getEmptyAdaptee();

    @Override
    public T adapt(ResultSetMap resultSet) {
        final T adaptee = getEmptyAdaptee();
        return adapt(adaptee, resultSet);
    }

    protected abstract T adapt(T adaptee, ResultSetMap resultSet);

}
