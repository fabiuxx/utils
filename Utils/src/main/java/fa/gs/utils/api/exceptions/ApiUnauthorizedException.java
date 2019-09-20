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
 * Clase que encapsula un error de autenticacion, indicando que se requiere una
 * autenticacion previa para la ejecucion de cierta operacion.
 *
 * @author Fabio A. González Sosa
 */
@ApplicationException(rollback = true)
public class ApiUnauthorizedException extends WebApplicationException {

    public ApiUnauthorizedException() {
        super();
    }

    public ApiUnauthorizedException(Throwable cause) {
        super(cause);
    }

    public ApiUnauthorizedException(Response response) {
        super(response);
    }

}
