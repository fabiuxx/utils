/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.crud;

import fa.gs.utils.database.jpa.Facade;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Editar {

    public static <T> Result<T> entity(Facade<T> facade, T entity) {
        Result<T> result;

        try {
            // Similar al caso de Create.
            entity = facade.edit(entity);
            result = Results.ok()
                    .value(entity)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrió un error modificando entidad")
                    .tag("entidad", entity.getClass().getCanonicalName())
                    .build();
        }

        return result;
    }

}
