/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.controllers;

import javax.ws.rs.core.Response;

/**
 *
 * @author Fabio A. González Sosa
 * @param <C> Tipo concreto de la accion de controlador.
 */
public abstract class RestControllerActionWithNoParams<C extends RestController> extends RestControllerAction<C, Void> {

    @Override
    public Response doAction(C ctx, Void param) throws Throwable {
        return doAction(ctx);
    }

    public abstract Response doAction(C ctx) throws Throwable;

}
