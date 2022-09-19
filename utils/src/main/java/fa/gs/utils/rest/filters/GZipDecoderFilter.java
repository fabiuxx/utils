/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.rest.filters;

import java.io.IOException;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

/**
 *
 * @author Fabio A. González Sosa
 */
@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class GZipDecoderFilter implements ReaderInterceptor {

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext ctx) throws IOException {
        String encoding = ctx.getHeaders().getFirst("Content-Encoding");
        if (encoding != null && Objects.equals("gzip", encoding.toLowerCase())) {
            GZIPInputStream is = new GZIPInputStream(ctx.getInputStream());
            ctx.setInputStream(is);
        }
        return ctx.proceed();
    }

}
