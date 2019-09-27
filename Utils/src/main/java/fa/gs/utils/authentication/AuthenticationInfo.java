/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface AuthenticationInfo extends SecurityContext, Principal, Serializable {

    UsuarioInfo getUsuario();

    Collection<PermisoInfo> getPermisos();

}
