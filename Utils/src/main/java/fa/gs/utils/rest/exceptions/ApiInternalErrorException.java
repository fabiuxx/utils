/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.exceptions;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Clase que encapsula un error interno no capturado durante alguna operacion de
 * la aplicación.
 *
 * @author Fabio A. González Sosa
 */
@ApplicationException(rollback = true)
public class ApiInternalErrorException extends WebApplicationException {

    public ApiInternalErrorException() {
        super("Error interno fatal.");
    }

    public ApiInternalErrorException(Throwable cause) {
        super("Error interno fatal.", cause);
    }

    public ApiInternalErrorException(Response response) {
        super("Error interno fatal.", response);
    }

}
