/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria.crud;

import fa.gs.utils.database.criteria.QueryCriteria;
import fa.gs.utils.database.facades.Facade;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Obtener {

    public static final <T> Result<T> pk(Facade<T> facade, Object id) {
        Result<T> result;

        try {
            T entidad = facade.find(id);
            result = Results.ok()
                    .value(entidad)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .message("Ocurrió un error obteniendo entidad")
                    .cause(thr)
                    .tag("entidad.id", id)
                    .build();
        }

        return result;
    }

    public static final <T> Result<Collection<T>> todos(Facade<T> facade) {
        return Obtener.todos(facade, null);
    }

    public static final <T> Result<Collection<T>> todos(Facade<T> facade, QueryCriteria criteria) {
        Result<Collection<T>> result;

        try {
            Collection<T> collection = (criteria != null) ? facade.find(criteria) : facade.findAll();
            Assertions.raiseIfNull(collection);
            result = Results.ok()
                    .value(collection)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrió un error obteniendo entidades")
                    .build();
        }

        return result;
    }

    public static final <T> Result<T> primero(Facade<T> facade, QueryCriteria criteria) {
        Result<T> result;

        try {
            T entidad = facade.findFirst(criteria);
            result = Results.ok()
                    .value(entidad)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrió un error obteniendo entidad")
                    .build();
        }

        return result;
    }

}
