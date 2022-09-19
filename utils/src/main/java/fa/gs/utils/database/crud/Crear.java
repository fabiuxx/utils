/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.crud;

import fa.gs.utils.database.jpa.facade.EntityFacade;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Crear {

    public static <T> Result<T> entity(EntityFacade<T> facade, T entity) {
        Result<T> result;

        try {
            /**
             * Aparentmente, la instancia recibida en este punto 'entity' se
             * altera localmente pero fuera de este metodo la instancia no queda
             * alterada ya que el parametro fue enviado remotamente (via
             * serializacion/RMI), por lo que tecnicamente son dos instancias
             * diferentes. Por lo tanto, se debe utilizar la entidad retornada
             * en el 'resultado' y desechar la entidad utilizada como parametro
             * de este metodo.
             */
            entity = facade.create(entity);
            result = Results.ok()
                    .value(entity)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrió un error creando entidad")
                    .tag("entidad", entity.getClass().getCanonicalName())
                    .build();
        }

        return result;
    }

}
