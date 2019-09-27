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
public class PasswordVerifier_BCRYPT implements PasswordVerifier {

    @Override
    public boolean verifies(String plain, String hashed) throws Throwable {
        return BCrypt.checkpw(plain, hashed);
    }

}
