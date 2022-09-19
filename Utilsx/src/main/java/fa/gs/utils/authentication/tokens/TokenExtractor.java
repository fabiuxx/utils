/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.tokens;

import fa.gs.utils.authentication.user.BaseAuthenticationInfo;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Fabio A. González Sosa
 */
public class TokenExtractor {

    public static String HEADER_NAME = HttpHeaders.AUTHORIZATION;

    public static String HEADER_AUTHENTICATION_SCHEME = BaseAuthenticationInfo.AUTHENTICATION_SCHEME;

    public static String COOKIE_NAME = "APP_SESSION_TOKEN";

    public static String fromHttpRequest(ContainerRequestContext request) {
        String value = fromHttpHeader(request);
        if (Assertions.stringNullOrEmpty(value)) {
            value = fromHttpCookie(request);
            if (Assertions.stringNullOrEmpty(value)) {
                value = fromHttpQueryParam(request);
            }
        }
        return value;
    }

    public static String fromHttpRequest(HttpServletRequest request) {
        String value = fromHttpHeader(request);
        if (Assertions.stringNullOrEmpty(value)) {
            value = fromHttpCookie(request);
            if (Assertions.stringNullOrEmpty(value)) {
                value = fromHttpQueryParam(request);
            }
        }
        return value;
    }

    public static String fromHttpHeader(ContainerRequestContext request) {
        String headerValue = request.getHeaderString(HEADER_NAME);
        return fromHttpHeader(headerValue);
    }

    public static String fromHttpHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER_NAME);
        return fromHttpHeader(authorizationHeader);
    }

    private static String fromHttpHeader(String headerValue) {
        if (Assertions.stringNullOrEmpty(headerValue)) {
            return null;
        }

        if (headerValue.startsWith(HEADER_AUTHENTICATION_SCHEME)) {
            headerValue = headerValue.substring(HEADER_AUTHENTICATION_SCHEME.length()).trim();
            return sanitizeUrlEncodedString(headerValue);
        }

        return sanitizeUrlEncodedString(headerValue);
    }

    public static String fromHttpCookie(ContainerRequestContext ctx) {
        Map<String, javax.ws.rs.core.Cookie> cookies = ctx.getCookies();
        if (Assertions.isNullOrEmpty(cookies)) {
            return null;
        }

        if (!cookies.containsKey(COOKIE_NAME)) {
            return null;
        }

        javax.ws.rs.core.Cookie cookie = cookies.get(COOKIE_NAME);
        return sanitizeUrlEncodedString(cookie.getValue());
    }

    public static String fromHttpCookie(HttpServletRequest request) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (Assertions.isNullOrEmpty(cookies)) {
            return null;
        }

        for (javax.servlet.http.Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), COOKIE_NAME)) {
                return sanitizeUrlEncodedString(cookie.getValue());
            }
        }

        return null;
    }

    public static String fromHttpQueryParam(ContainerRequestContext ctx) {
        try {
            List<String> values = ctx.getUriInfo().getQueryParameters().get("session_token");
            String token = Lists.first(values);
            if (Assertions.stringNullOrEmpty(token)) {
                return null;
            } else {
                return sanitizeUrlEncodedString(token);
            }
        } catch (Throwable thr) {
            Errors.dump(System.err, thr, "Ocurrió un error obteniendo token desde query");
            return null;
        }
    }

    public static String fromHttpQueryParam(HttpServletRequest request) {
        try {
            String token = request.getParameter("session_token");
            if (Assertions.stringNullOrEmpty(token)) {
                return null;
            } else {
                return sanitizeUrlEncodedString(token);
            }
        } catch (Throwable thr) {
            Errors.dump(System.err, thr, "Ocurrió un error obteniendo token desde query");
            return null;
        }
    }

    private static String sanitizeUrlEncodedString(String value) {
        try {
            String decoded = URLDecoder.decode(value, "UTF-8");
            return decoded.trim();
        } catch (Throwable thr) {
            return value;
        }
    }

}
