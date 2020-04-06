/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.passwords;

import fa.gs.utils.misc.Hashes;
import fa.gs.utils.misc.text.Strings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PasswordHasher_SHA256 implements PasswordHasher {

    @Override
    public String hash(String plain) throws Throwable {
        byte[] bytes = Strings.getBytes(plain);
        return Hashes.sha256AsHexString(bytes);
    }

}
