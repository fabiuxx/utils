/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import py.com.generica.utils.crypto.SHA256;
import py.com.generica.utils.misc.text.Strings;

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

}
