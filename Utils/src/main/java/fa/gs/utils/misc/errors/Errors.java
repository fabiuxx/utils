/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.errors;

import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.result.utils.Failure;
import java.io.PrintStream;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Errors {

    public static Errno errno(String codigo, String descripcion) {
        return new ErrnoImpl(codigo, descripcion);
    }

    public static AppErrorException.Builder builder() {
        return new AppErrorExceptionBuilder();
    }

    public static AppErrorException failure(Failure failure) {
        return new AppErrorException(failure);
    }

    public static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("TODO");
    }

    public static void popStackTrace(Throwable throwable) {
        popStackTrace(throwable, 1);
    }

    public static void popStackTrace(Throwable throwable, int n) {
        StackTraceElement[] originalStackTrace = throwable.getStackTrace();
        StackTraceElement[] cleanedUpStackTrace = new StackTraceElement[originalStackTrace.length - n];
        System.arraycopy(originalStackTrace, n, cleanedUpStackTrace, 0, cleanedUpStackTrace.length);
        throwable.setStackTrace(cleanedUpStackTrace);
    }

    public static void dump(PrintStream stream, Throwable thr) {
        dumpThrowable(stream, thr, 0);
    }

    private static void dumpThrowable(PrintStream stream, Throwable thr, int ident) {
        if (thr == null) {
            return;
        }

        synchronized (stream) {
            try {
                String spaces = ident(ident);

                String msg = Strings.format("%sERROR: %s / %s (%s)", spaces, thr.getMessage(), thr.getLocalizedMessage(), thr.getClass().getCanonicalName());
                stream.println(msg);

                for (StackTraceElement element : thr.getStackTrace()) {
                    String ste = Strings.format("%s%s", spaces, element.toString());
                    stream.println(ste);
                }

                Throwable[] suppreseds = thr.getSuppressed();
                if (suppreseds != null && suppreseds.length > 0) {
                    stream.println("---------------------- <START SUPRESSED> ----------------------");
                    for (Throwable supressed : suppreseds) {
                        dumpThrowable(stream, supressed, ident);
                    }
                    stream.println("----------------------- <END SUPRESSED> -----------------------");
                }
            } finally {
                stream.flush();
            }

            dumpThrowable(stream, thr.getCause(), ident + 4);
        }
    }

    private static String ident(int ident) {
        StringBuilder builder = new StringBuilder();
        while (ident > 0) {
            builder.append(" ");
            ident--;
        }
        return builder.toString();
    }

    private static class ErrnoImpl implements Errno {

        private final String codigo;
        private final String descriptor;

        ErrnoImpl(String codigo, String descriptor) {
            this.codigo = codigo;
            this.descriptor = descriptor;
        }

        @Override
        public String getDescriptor() {
            return descriptor;
        }

        @Override
        public String getCode() {
            return codigo;
        }

    }
}
