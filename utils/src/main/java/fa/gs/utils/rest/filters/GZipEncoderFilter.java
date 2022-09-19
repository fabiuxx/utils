/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.filters;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 *
 * @author Fabio A. González Sosa
 */
@Provider
@Priority(Priorities.USER)
public class GZipEncoderFilter implements WriterInterceptor {

    @Override
    public void aroundWriteTo(WriterInterceptorContext ctx) throws IOException, WebApplicationException {
        GZIPOutputStream os = new GZIPOutputStream(ctx.getOutputStream());
        ctx.getHeaders().putSingle("Content-Encoding", "gzip");
        ctx.setOutputStream(os);
        ctx.proceed();
    }

}
