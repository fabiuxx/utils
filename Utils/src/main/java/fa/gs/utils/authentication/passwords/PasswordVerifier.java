/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.passwords;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface PasswordVerifier {

    public boolean verifies(String plain, String hashed) throws Throwable;

}
