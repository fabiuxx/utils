/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc.errors;

import java.util.Map;
import py.com.generica.utils.result.utils.Failure;

/**
 *
 * @author Fabio A. González Sosa
 */
public class AppErrorException extends Exception {

    private final Failure failure;

    AppErrorException(Failure failure) {
        super(failure.message(), failure.cause());
        this.failure = failure;
    }

    public String message() {
        return failure.message();
    }

    public Throwable cause() {
        return failure.cause();
    }

    public int errno() {
        return failure.errno();
    }

    public Map<String, Object> tags() {
        return failure.tags();
    }

    public interface Builder {

        public AppErrorException.Builder failure(Failure failure);

        public AppErrorException.Builder cause(Throwable thr);

        public AppErrorException.Builder message(String fmt, Object... args);

        public AppErrorException.Builder errno(int errno);

        public AppErrorException.Builder tag(String tag, Object value);

        public AppErrorException.Builder tags(Map<String, Object> tags);

        public AppErrorException build();

        public void raise() throws AppErrorException;

    }
}
