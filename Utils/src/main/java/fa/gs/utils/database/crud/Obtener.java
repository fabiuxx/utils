/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.crud;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.jpa.Facade;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Obtener {

    public static final <T> Result<T> porId(Facade<T> facade, Object id) {
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

    public static final <T> Result<T> primero(Facade<T> facade) {
        Result<T> result;

        try {
            Collection<T> collection = facade.findAll();
            T entidad = Lists.first(collection);
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

    public static final <T> Result<Collection<T>> todos(Facade<T> facade) {
        Result<Collection<T>> result;

        try {
            Collection<T> collection = facade.findAll();
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

}
