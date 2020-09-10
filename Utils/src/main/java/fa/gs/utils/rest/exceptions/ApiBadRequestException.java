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
 * Clase que encapsula un error debido a datos incompletos o con un formato
 * incorrecto, recibidos como parte de una peticion.
 *
 * @author Fabio A. González Sosa
 */
@ApplicationException(rollback = true)
public class ApiBadRequestException extends WebApplicationException {

    public ApiBadRequestException() {
        super("Error de formato para cuerpo de petición.");
    }

    public ApiBadRequestException(Throwable cause) {
        super("Error de formato para cuerpo de petición.", cause);
    }

    public ApiBadRequestException(Response response) {
        super("Error de formato para cuerpo de petición.", response);
    }

}
