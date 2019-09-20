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
 * Clase que encapsula un error en la aplicacion web que debe forzar al
 * contenedor a desechar todas las operaciones realizadas, principalmente con la
 * base de datos. La anotación
 * {@link javax.ejb.ApplicationException ApplicationException} indica
 * precisamente al contenedor de aplicaciones que cuando esta excepcion es
 * lanzada, las transacciones JPA deben ser "rollbackeadas".
 * <br>
 * Fuentes:
 * <ul>
 * <li><a href="https://stackoverflow.com/a/19563956">[1]</a></li>
 * <li><a href="https://docs.oracle.com/javaee/7/api/javax/ejb/ApplicationException.html">[2]</a></li>
 * </ul>
 *
 * @author Fabio A. González Sosa
 */
@ApplicationException(rollback = true)
public class ApiRollbackException extends WebApplicationException {

    public ApiRollbackException() {
        super();
    }

    public ApiRollbackException(Throwable cause) {
        super(cause);
    }

    public ApiRollbackException(Response response) {
        super(response);
    }

}
