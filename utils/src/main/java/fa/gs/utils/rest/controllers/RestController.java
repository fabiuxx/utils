/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.controllers;

import fa.gs.utils.logging.app.AppLogger;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.AppErrorException;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.rest.exceptions.ApiBadRequestException;
import fa.gs.utils.rest.exceptions.ApiRollbackException;
import fa.gs.utils.rest.exceptions.ApiUnauthorizedException;
import fa.gs.utils.rest.responses.ServiceResponse;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.ws.rs.core.Response;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class RestController implements Serializable {

    /**
     * Permite encapsular un bloque de codigo ejecutable y generar una respuesta
     * HTTP valida.
     *
     * @param supplier Funcion lambda o instancia capaz de producir una
     * instancia de {@link javax.ws.rs.core.Response Response}.
     * @return Respuesta HTTP.
     */
    protected Response wrap(Supplier<Response> supplier) {
        Response response;

        try {
            response = supplier.get();
        } catch (Throwable thr) {
            response = deriveResponseFromError(thr);
        }

        return response;
    }

    /**
     * Permite generar una respuesta HTTP en base a una excepcion producida.
     *
     * @param cause Excepcion producida.
     * @return Respuesta HTTP.
     */
    protected Response deriveResponseFromError(Throwable cause) {
        Response response;

        if (cause instanceof IllegalArgumentException || cause instanceof IllegalStateException || cause instanceof ApiBadRequestException) {
            // Peticion incorrecta.
            logError(cause, "Error de formato para cuerpo de petición.");
            response = ServiceResponse.badRequest()
                    .cause(cause)
                    .build();
        } else if (cause instanceof ApiUnauthorizedException) {
            // Peticion no autorizada.
            logError(cause, "Error de autenticación para petición.");
            response = ServiceResponse.unauthorized()
                    .cause(cause)
                    .build();
        } else if (cause instanceof ApiRollbackException) {
            // Operacion marcada para rollback.
            logError(cause, "Rollback.");
            response = ((ApiRollbackException) cause).getResponse();
            if (response == null) {
                response = ServiceResponse.ko()
                        .cause("Ocurrió un error que forzó a la operación desechar sus resultados (rollback)")
                        .build();
            }
        } else if (cause instanceof AppErrorException) {
            // Error de aplicacion.
            logError(cause, "Error de aplicación.");
            response = ServiceResponse.ko()
                    .cause(((AppErrorException) cause))
                    .errno(((AppErrorException) cause).errno())
                    .build();
        } else {
            // Error no esperado.
            logError(cause, "Error interno fatal.");
            response = ServiceResponse.error().cause(cause).build();
        }

        return response;
    }

    /**
     * Permite aplicar logica opcional previa a la ejecucion de la accion.
     *
     * @param action Accion a ejectuar.
     */
    protected void beforeExecuteControllerAction(RestControllerAction action) {
        ;
    }

    /**
     * Permite aplicar logica opcional posterior a la ejecucion de la accion.
     *
     * @param action Accion ejecutada.
     * @param response Respuesta obtenida tras ejecucion.
     */
    protected void afterExecuteControllerAction(RestControllerAction action, Response response) {
        ;
    }

    /**
     * Ejecuta un bloque logico de "accion" que no recibe parametros.
     *
     * @param actionClass Clase que define la accion a ejecutar.
     * @return Respuesta de la operacion.
     */
    public Response executeControllerAction(Class<? extends RestControllerAction> actionClass) {
        return executeControllerAction(actionClass, null);
    }

    /**
     * Ejecuta un bloque logico de "accion" que recibe un objeto como parametro.
     *
     * @param actionClass Clase que define la accion a ejecutar.
     * @param param Objeto de parametro.
     * @return Respuesta de la operacion.
     */
    public Response executeControllerAction(Class<? extends RestControllerAction> actionClass, Object param) {
        try {
            // Instanciar clase.
            RestControllerAction action = Reflection.tryCreateInstance(actionClass);
            if (action == null) {
                throw Errors.illegalState("No se pudo instanciar la clase '%s'.", actionClass.getCanonicalName());
            }

            // Aplicar logica adicional de preinicializacion.
            beforeExecuteControllerAction(action);

            // Ejecutar accion.
            Response response = action.doAction(param);

            // Aplicar logica adicional de postejecucion.
            afterExecuteControllerAction(action, response);

            // Respuesta final.
            return response;
        } catch (Throwable thr) {
            return deriveResponseFromError(thr);
        }
    }

    /**
     * Obtiene la instancia de logger de aplicacion.
     *
     * @return Logger de aplicacion.
     */
    public abstract AppLogger getLog();

    /**
     * Emite un mensaje especifico de error.
     *
     * @param thr Excepcion capturada, si hubiere.
     * @param msg Mensaje de ayuda que indica la causa del error.
     */
    private void logError(Throwable thr, String msg) {
        AppLogger logger = getLog();
        if (logger == null) {
            Errors.dump(System.err, thr);
        } else {
            logger.error(thr, msg);
        }
    }

}
