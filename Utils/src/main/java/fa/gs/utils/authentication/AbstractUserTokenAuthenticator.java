/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication;

import fa.gs.utils.authentication.tokens.TokenDecoder;
import fa.gs.utils.authentication.user.AuthenticationInfo;
import fa.gs.utils.misc.Args;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo par ainformacion de usuario.
 * @param <Q> Parametro de tipo para tipo de payload de token.
 */
public abstract class AbstractUserTokenAuthenticator<Q> implements Authenticator<AuthenticationInfo> {

    @Override
    @Deprecated
    public final Result<AuthenticationInfo> authenticate(Object... params) {
        String token = Args.argv(params, 0);
        return authenticateUserToken(token);
    }

    public Result<AuthenticationInfo> authenticateUserToken(String token) {
        Result<AuthenticationInfo> result;

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
            AuthenticationInfo authenticationInfo = parseTokenPayload(payload);
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

    protected abstract AuthenticationInfo parseTokenPayload(Q payload) throws Throwable;

}
