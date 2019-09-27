/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Assertions;
import java.security.Principal;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractAuthenticationInfo implements AuthenticationInfo {

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
    public AbstractAuthenticationInfo() {
        this.usuario = null;
        this.permisos = Lists.empty();
    }

    @Override
    public String getName() {
        return (usuario != null) ? usuario.username() : null;
    }

    @Override
    public Principal getUserPrincipal() {
        return this;
    }

    @Override
    public boolean isUserInRole(String roleName) {
        // Control de seguridad.
        if (usuario == null) {
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
        for (PermisoInfo permiso : permisos) {
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
    public UsuarioInfo getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioInfo usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<PermisoInfo> getPermisos() {
        return permisos;
    }

    public void setPermisos(Collection<PermisoInfo> permisos) {
        this.permisos = permisos;
    }
    //</editor-fold>

}
