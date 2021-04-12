/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.errors;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.misc.text.Text;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.utils.Failure;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Errors {

    public static Errno errno(String errno) {
        if (Assertions.stringNullOrEmpty(errno) || errno.length() < 3) {
            throw Errors.illegalArgument("Valor no válido.");
        }

        String descriptor = errno.substring(0, 3);
        String codigo = errno.substring(3);
        return errno(descriptor, codigo);
    }

    public static Errno errno(String descripcion, int codigo) {
        codigo = Math.abs(codigo);
        codigo = Math.min(codigo, 999999);
        String codigo0 = Strings.format("%06d", codigo);
        return errno(descripcion, codigo0);
    }

    public static Errno errno(String descripcion, String codigo) {
        return new ErrnoImpl(descripcion, codigo);
    }

    public static boolean inRange(Errno errno, int min, int max) {
        try {
            int code = Integer.parseInt(errno.getCode());
            return (code >= min && code <= max);
        } catch (Throwable thr) {
            Errors.dump(System.err, thr);
            return false;
        }
    }

    public static UnsupportedOperationException unsupported() {
        return unsupported("TODO");
    }

    public static UnsupportedOperationException unsupported(String fmt, Object... args) {
        String msg = Strings.format(fmt, args);
        return new UnsupportedOperationException(msg);
    }

    public static IllegalArgumentException illegalArgument() {
        return illegalArgument("ILLEGAL ARGUMENT");
    }

    public static IllegalArgumentException illegalArgument(String fmt, Object... args) {
        return illegalArgument(null, fmt, args);
    }

    public static IllegalArgumentException illegalArgument(Throwable cause, String fmt, Object... args) {
        String msg = Strings.format(fmt, args);
        return new IllegalArgumentException(msg, cause);
    }

    public static IllegalStateException illegalState() {
        return illegalState("ILLEGAL STATE");
    }

    public static IllegalStateException illegalState(String fmt, Object... args) {
        return illegalState(null, fmt, args);
    }

    public static IllegalStateException illegalState(Throwable cause, String fmt, Object... args) {
        String msg = Strings.format(fmt, args);
        return new IllegalStateException(msg, cause);
    }

    public static IOException io() {
        return io("I/O");
    }

    public static IOException io(String fmt, Object... args) {
        return io(null, fmt, args);
    }

    public static IOException io(Throwable cause, String fmt, Object... args) {
        String msg = Strings.format(fmt, args);
        return new IOException(msg, cause);
    }

    public static AppErrorException.Builder builder() {
        return new AppErrorExceptionBuilder();
    }

    public static AppErrorException failure(Failure failure) {
        return new AppErrorException(failure);
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

    public synchronized static void dump(PrintStream stream, Result result) {
        if (result.isFailure()) {
            Failure failure = result.failure();
            dump(stream, failure.cause(), Text.select(failure.message(), "ERROR"));
        }
    }

    public synchronized static void dump(PrintStream stream, Throwable thr) {
        dumpThrowable(stream, thr, 0);
    }

    public synchronized static void dump(PrintStream stream, Throwable thr, String fmt, Object... args) {
        String msg = Strings.format(fmt, args);
        stream.println(msg);
        dumpThrowable(stream, thr, 0);
    }

    private static void dumpThrowable(PrintStream stream, Throwable thr, int ident) {
        if (thr == null) {
            return;
        }

        synchronized (stream) {
            try {
                String spaces = Text.ident(ident);

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

    private static class ErrnoImpl implements Errno {

        private final String codigo;
        private final String descriptor;

        ErrnoImpl(String descriptor, String codigo) {
            if (Assertions.stringNullOrEmpty(codigo) || codigo.length() != 6) {
                throw Errors.illegalArgument("El valor del 'código' debe ser una cadena de 6 caracteres");
            }

            if (Assertions.stringNullOrEmpty(descriptor) || descriptor.length() != 3) {
                throw Errors.illegalArgument("El valor del 'descriptor' debe ser una cadena de 3 caracteres");
            }

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

        @Override
        public String getErrnoString() {
            return Strings.format("%s%s", getDescriptor(), getCode());
        }

    }
}
