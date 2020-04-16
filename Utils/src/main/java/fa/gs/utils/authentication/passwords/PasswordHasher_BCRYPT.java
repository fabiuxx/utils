/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.passwords;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PasswordHasher_BCRYPT implements PasswordHasher {

    PasswordHasher_BCRYPT() {
        ;
    }

    @Override
    public String hash(String plain) throws Throwable {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

}
