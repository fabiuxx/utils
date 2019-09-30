/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication;

import fa.gs.utils.result.simple.Result;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Authenticator<T> extends Serializable {

    public Result<T> authenticate(Object... params);

}
