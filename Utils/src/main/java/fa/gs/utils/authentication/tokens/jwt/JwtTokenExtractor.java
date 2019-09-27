/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.tokens.jwt;

import fa.gs.utils.authentication.AbstractAuthenticationInfo;
import fa.gs.utils.misc.Assertions;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JwtTokenExtractor {

    public static final String HEADER_NAME = HttpHeaders.AUTHORIZATION;

    public static final String HEADER_AUTHENTICATION_SCHEME = AbstractAuthenticationInfo.AUTHENTICATION_SCHEME;

    public static final String COOKIE_NAME = "APP_SESSION_TOKEN";

    public static String fromHttpRequest(ContainerRequestContext request) {
        String value = fromHttpHeader(request);
        if (Assertions.stringNullOrEmpty(value)) {
            value = fromHttpCookie(request);
        }
        return value;
    }

    public static String fromHttpRequest(HttpServletRequest request) {
        String value = fromHttpHeader(request);
        if (Assertions.stringNullOrEmpty(value)) {
            value = fromHttpCookie(request);
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

    private static String sanitizeUrlEncodedString(String value) {
        try {
            String decoded = URLDecoder.decode(value, "UTF-8");
            return decoded.trim();
        } catch (Throwable thr) {
            return value;
        }
    }

}
