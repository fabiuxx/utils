/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication;

import fa.gs.utils.authentication.tokens.TokenDecoder;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 * @param <Q>
 */
public abstract class AbstractAuthenticator<T extends AuthenticationInfo, Q> {

    public Result<T> authenticate(String token) {
        Result<T> result;

        try {
            // Decodificar Token
            TokenDecoder<Q> decoder = getTokenDecoder();
            Q payload = decoder.decodeToken(token);
            if (payload == null) {
                throw Errors.builder()
                        .message("Tóken invalido")
                        .build();
            }

            // Crear contexto de autenticacion.
            T authenticationInfo = parseTokenPayload(payload);
            result = Results.ok()
                    .value(authenticationInfo)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Credenciales inválidas")
                    .build();
        }

        return result;
    }

    protected abstract TokenDecoder<Q> getTokenDecoder();

    protected abstract T parseTokenPayload(Q payload) throws Throwable;

}
