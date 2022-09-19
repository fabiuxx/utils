/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.responses;

import fa.gs.utils.collections.Arrays;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Fabio A. González Sosa
 */
public class BytesStream implements StreamingOutput, Serializable {

    private final byte[] bytes;

    public BytesStream(byte[] bytes) {
        this.bytes = bytes;
    }

    public int totalBytes() {
        return Arrays.size(bytes);
    }

    @Override
    public void write(OutputStream output) throws IOException, WebApplicationException {
        output.write(bytes, 0, totalBytes());
    }

}
