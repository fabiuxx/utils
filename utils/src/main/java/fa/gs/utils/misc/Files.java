/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.misc.text.Charsets;
import fa.gs.utils.misc.text.StringBuilder2;
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
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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

    public static void deleteFolderRecursively(File folder) throws IOException {
        if (folder.exists() && folder.isDirectory()) {
            Path path = folder.toPath();
            java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    delete(dir.toFile());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    delete(file.toFile());
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    public static void touch(File file) throws IOException {
        if (!file.exists()) {
            try ( PrintWriter writer = new PrintWriter(file)) {
                writer.write("");
                writer.flush();
            }
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
        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), charset));
    }

}
