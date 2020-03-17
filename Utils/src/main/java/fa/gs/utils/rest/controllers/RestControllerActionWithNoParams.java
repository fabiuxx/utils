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
 */
public abstract class RestControllerActionWithNoParams implements RestControllerAction<Void> {

    @Override
    public Response doAction(Void param) throws Throwable {
        return doAction();
    }

    public abstract Response doAction() throws Throwable;

}
