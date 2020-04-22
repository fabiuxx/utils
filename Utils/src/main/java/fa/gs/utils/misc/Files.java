/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.crypto.SHA256;
import fa.gs.utils.misc.text.StringBuilder2;
import fa.gs.utils.misc.text.Strings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        try (FileOutputStream fos = new FileOutputStream(target)) {
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
        try (InputStream is = new FileInputStream(file)) {
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
        InputStream is = new FileInputStream(file);
        return readAll(is);
    }

    public static String readAll(InputStream is) throws IOException {
        StringBuilder2 sb = new StringBuilder2();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            sb.appendln(line);
        }
        return sb.toString();
    }

}
