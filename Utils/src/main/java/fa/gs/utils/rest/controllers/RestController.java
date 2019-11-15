/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.controllers;

import fa.gs.utils.logging.app.AppLogger;
import fa.gs.utils.misc.errors.AppErrorException;
import fa.gs.utils.rest.exceptions.ApiBadRequestException;
import fa.gs.utils.rest.exceptions.ApiRollbackException;
import fa.gs.utils.rest.responses.ServiceResponse;
import java.io.Serializable;
import javax.ws.rs.core.Response;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class RestController implements Serializable {

    /**
     * Ejecuta un bloque logico de "accion" que no recibe parametros.
     *
     * @param action Accion a ejecutar.
     * @return Respuesta de la operacion.
     */
    public Response executeControllerAction(RestControllerAction action) {
        return executeControllerAction(action, null);
    }

    /**
     * Ejecuta un bloque logico de "accion" que recibe un objeto como parametro.
     *
     * @param action Accion a ejecutar.
     * @param param Objeto de parametro.
     * @return Respuesta de la operacion.
     */
    public Response executeControllerAction(RestControllerAction action, Object param) {
        Response response;
        try {
            // Control de seguridad.
            if (action == null) {
                return ServiceResponse.error()
                        .cause("No se puede resolver la petición en este momento")
                        .build();
            }

            /**
             * Validar parametros de entrada.
             *
             * Se encapsula esta operacion de manera a que se capture cualquier
             * clase de excepcion que pudiera ocurrir, no necesariamente
             * aquellas relacionadas a validaciones propias. Es posible que
             * ocurran otras excepciones indpendientes a los metodos de
             * validacion utilizados, por ejemplo, excepciones en las entidades
             * cuando uno o varios campos no existen en estados validos.
             */
            try {
                action.validateParam(this, param);
            } catch (Throwable thr) {
                throw new ApiBadRequestException(thr);
            }

            /**
             * Ejecutar la accion.
             *
             * Se espera como resultado una respuesta valida procesable por el
             * contenedor de aplicaciones.
             */
            response = action.doAction(this, param);
        } catch (IllegalArgumentException | ApiBadRequestException thr1) {
            // Peticion incorrecta.
            logError(thr1, action, "Error de formato para cuerpo de petición");
            response = ServiceResponse.badRequest().build();
        } catch (ApiRollbackException thr2) {
            // Operacion marcada para rollback.
            logError(thr2, action, "Rollback");
            response = thr2.getResponse();
            if (response == null) {
                response = ServiceResponse.ko().cause("Ocurrió un error que forzó a la operación desechar sus resultados (rollback)").build();
            }
        } catch (AppErrorException thr3) {
            // Error de aplicacion.
            logError(thr3, action, "Error de aplicación");
            response = ServiceResponse.ko().cause(thr3.message()).build();
        } catch (Throwable thr4) {
            // Error no esperado.
            logError(thr4, action, "Error interno fatal");
            response = ServiceResponse.error().cause(thr4).build();
        }

        return response;
    }

    /**
     * Unifica la obtencion del nombre de una accion rest.
     *
     * @param action Accion rest.
     * @return Nombre unico de la accion rest.
     */
    public String getActionName(RestControllerAction action) {
        return action.getClass().getCanonicalName();
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
     * @param action Accion rest que fallo.
     * @param msg Mensaje de ayuda que indica la causa del error.
     */
    private void logError(Throwable thr, RestControllerAction action, String msg) {
        getLog().error()
                .cause(thr)
                .message(msg)
                .tag("action.name", getActionName(action))
                .log();
    }

}
