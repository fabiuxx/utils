/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.api.exceptions;

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
        super();
    }

    public ApiInternalErrorException(Throwable cause) {
        super(cause);
    }

    public ApiInternalErrorException(Response response) {
        super(response);
    }

}
