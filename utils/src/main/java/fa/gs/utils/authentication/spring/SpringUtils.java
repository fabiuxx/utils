/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.spring;

import fa.gs.utils.misc.Assertions;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SpringUtils {

    public static Authentication getAuthenticationContext() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            return null;
        }
        return ctx.getAuthentication();
    }

    public static boolean isAuthenticated() {
        Authentication auth = SpringUtils.getAuthenticationContext();
        return SpringUtils.isAuthenticated(auth);
    }

    public static boolean isAuthenticated(final Authentication auth) {
        // Control de seguridad.
        if (auth == null) {
            return false;
        }

        /**
         * Spring genera un tipo anonimo de autenticacion por defecto. Fuente:
         * https://stackoverflow.com/a/26117007/1284724.
         */
        boolean isAnonymous = (auth instanceof AnonymousAuthenticationToken);
        return !isAnonymous;
    }

    public static boolean hasRole(Authentication auth, String role) {
        if (auth == null) {
            return false;
        }

        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (Assertions.equals(authority.getAuthority(), role)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasAllRoles(Authentication auth, String... roles) {
        for (String role : roles) {
            boolean ok = SpringUtils.hasRole(auth, role);
            if (!ok) {
                return false;
            }
        }

        return true;
    }

    public static boolean hasAnyRole(Authentication auth, String... roles) {
        for (String role : roles) {
            boolean ok = SpringUtils.hasRole(auth, role);
            if (ok) {
                return true;
            }
        }

        return false;
    }

}
