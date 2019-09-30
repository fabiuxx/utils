/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.user;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Assertions;
import java.security.Principal;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public class BaseAuthenticationInfo implements AuthenticationInfo {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Prefijo a utilizar para denotar nombres de perfiles/roles de usuario.
     */
    private static final String ROLE_NAME_PREFIX = "ROLE_";

    /**
     * Constante que indica el esquema de autenticacion (HTTP 1.1) soportado.
     */
    public static final String AUTHENTICATION_SCHEME = "Bearer";

    /**
     * Informacion de usuario.
     */
    private UsuarioInfo usuario;

    /**
     * Informacion de permisos.
     */
    private Collection<PermisoInfo> permisos;

    /**
     * Constructor.
     */
    public BaseAuthenticationInfo() {
        this.usuario = null;
        this.permisos = Lists.empty();
    }

    @Override
    public String getName() {
        return (getUsuario() != null) ? getUsuario().username() : null;
    }

    @Override
    public Principal getUserPrincipal() {
        return this;
    }

    @Override
    public boolean isUserInRole(String roleName) {
        // Control de seguridad.
        if (getUsuario() == null) {
            return false;
        }

        // Control de seguridad.
        if (Assertions.stringNullOrEmpty(roleName)) {
            return false;
        }

        // Normalizar nombre de rol.
        if (!roleName.startsWith(ROLE_NAME_PREFIX)) {
            roleName = ROLE_NAME_PREFIX + roleName;
        }

        // Verificar si el usuario tiene asignado el permiso indicado.
        for (PermisoInfo permiso : getPermisos()) {
            if (Objects.equals(roleName, permiso.name())) {
                return true;
            }
        }

        // No tiene el permiso asignado.
        return false;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return AUTHENTICATION_SCHEME;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    @Override
    public final UsuarioInfo getUsuario() {
        return usuario;
    }

    public final void setUsuario(UsuarioInfo usuario) {
        this.usuario = usuario;
    }

    @Override
    public final Collection<PermisoInfo> getPermisos() {
        return permisos;
    }

    public final void setPermisos(Collection<PermisoInfo> permisos) {
        this.permisos = permisos;
    }
    //</editor-fold>

}
