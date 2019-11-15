/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.controllers;

import java.io.Serializable;
import javax.ws.rs.core.Response;

/**
 *
 * @author Fabio A. González Sosa
 * @param <C> Tipo concreto de la accion de controlador.
 * @param <P> Tipo concreto de parametro recibido, si hubiere.
 */
public abstract class RestControllerAction<C extends RestController, P> implements Serializable {

    /**
     * Permite realizar operaciones de validacion antes de realizar la accion
     * implementada.
     *
     * @param ctx Contexto de ejecucion.
     * @param param Parametros de entrada.
     * @throws IllegalArgumentException Si el parametro es invalido.
     */
    public void validateParam(C ctx, P param) {
        ;
    }

    /**
     * Realiza una accion especifica.
     *
     * @param ctx Contexto de ejecucion.
     * @param param Parametros de entrada.
     * @return Respuesta HTTP que comprende el resultado de la accion.
     * @throws Exception Si ocurre algun error durante la ejecucion de la
     * accion.
     */
    public abstract Response doAction(C ctx, P param) throws Throwable;

}
