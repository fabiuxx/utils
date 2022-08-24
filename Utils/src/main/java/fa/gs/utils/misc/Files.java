/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.crypto.SHA256;
import fa.gs.utils.misc.text.Charsets;
import fa.gs.utils.misc.text.StringBuilder2;
import fa.gs.utils.misc.text.Strings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Files {

    public static File createTemporary() throws IOException {
        return createTemporary(Ids.randomUuid(), ".tmp");
    }

    public static File createTemporary(String name, String extension) throws IOException {
        File file = File.createTempFile(name, extension, null);
        return file;
    }

    public static File dump(InputStream input) throws IOException {
        File target = createTemporary();
        dump(input, target);
        return target;
    }

    public static void dump(InputStream input, File target) throws IOException {
        try ( FileOutputStream fos = new FileOutputStream(target)) {
            byte[] buffer = new byte[4098];
            int read;
            while ((read = input.read(buffer)) > 0) {
                fos.write(buffer, 0, read);
            }
        }
    }

    public static void delete(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    public static String hash(File file) throws Throwable {
        MessageDigest digest = MessageDigest.getInstance(SHA256.ALGORITHM_FAMILY);
        try ( InputStream is = new FileInputStream(file)) {
            byte[] block = new byte[4096];
            int read;
            while ((read = is.read(block)) > 0) {
                digest.update(block, 0, read);
            }
            byte[] hash = digest.digest();
            return Strings.bytesToHexString(hash);
        }
    }

    public static String readAll(File file) throws IOException {
        return readAll(file, Charsets.UTF8);
    }

    public static String readAll(File file, Charset charset) throws IOException {
        byte[] bytes = java.nio.file.Files.readAllBytes(file.toPath());
        return new String(bytes, charset);
    }

    @Deprecated
    public static String readAll(InputStream is) throws IOException {
        StringBuilder2 sb = new StringBuilder2();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            sb.appendln(line);
        }
        return sb.toString();
    }

    public static BufferedReader getReader(File file) throws IOException {
        return getReader(file, Charsets.UTF8);
    }

    public static BufferedReader getReader(File file, Charset charset) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    public static PrintWriter getWriter(File file) throws IOException {
        return getWriter(file, Charsets.UTF8);
    }

    public static PrintWriter getWriter(File file, Charset charset) throws IOException {
        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }

}
