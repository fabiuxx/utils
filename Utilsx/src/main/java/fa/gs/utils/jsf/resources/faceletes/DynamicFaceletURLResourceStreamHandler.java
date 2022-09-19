/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.faceletes;

import fa.gs.utils.misc.text.Charsets;
import fa.gs.utils.misc.text.Strings;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 *
 * @author Fabio A. González Sosa
 */
public class DynamicFaceletURLResourceStreamHandler extends URLStreamHandler {

    private final String content;

    public DynamicFaceletURLResourceStreamHandler(String content) {
        this.content = content;
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new Connection(url, content);
    }

    private static class Connection extends URLConnection {

        private final byte[] content;

        public Connection(URL url, String content) {
            super(url);
            this.content = Strings.getBytes(content);
        }

        @Override
        public String getContentEncoding() {
            return Charsets.UTF8.name();
        }

        @Override
        public String getContentType() {
            return "text/xml";
        }

        @Override
        public long getContentLengthLong() {
            return content.length;
        }

        @Override
        public int getContentLength() {
            return content.length;
        }

        @Override
        public void connect() throws IOException {
            ;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }

    }

}
