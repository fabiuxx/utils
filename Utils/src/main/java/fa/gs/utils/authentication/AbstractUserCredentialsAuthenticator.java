/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication;

import fa.gs.utils.authentication.user.AuthenticationInfo;
import fa.gs.utils.misc.Args;
import fa.gs.utils.result.simple.Result;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractUserCredentialsAuthenticator implements Authenticator<AuthenticationInfo> {

    @Override
    @Deprecated
    public final Result<AuthenticationInfo> authenticate(Object... params) {
        String username = Args.argv(params, 0);
        String password = Args.argv(params, 1);
        return authenticateUserCredentials(username, password);
    }

    public abstract Result<AuthenticationInfo> authenticateUser(Object id);

    public abstract Result<AuthenticationInfo> authenticateUserCredentials(String username, String password);

}
