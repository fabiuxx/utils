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
 * @param <P> Tipo concreto de parametro recibido, si hubiere.
 */
public interface RestControllerAction<P> extends Serializable {

    /**
     * Realiza una accion especifica.
     *
     * @param param Parametros de entrada.
     * @return Respuesta HTTP que comprende el resultado de la accion.
     * @throws Exception Si ocurre algun error durante la ejecucion de la
     * accion.
     */
    Response doAction(P param) throws Throwable;

}
