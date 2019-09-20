/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Fabio A. González Sosa
 */
public class EmptyInputStream extends InputStream {

    @Override
    public int read() throws IOException {
        return -1;
    }

}
