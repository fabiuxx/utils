/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.errors;

import fa.gs.utils.result.utils.Failure;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class AppErrorException extends Exception {

    private final Failure failure;

    AppErrorException(Failure failure) {
        super(Errors.message(failure.cause()), failure.cause());
        this.failure = failure;
    }

    public Throwable cause() {
        return failure.cause();
    }

    public Errno errno() {
        return failure.errno();
    }

    public Map<String, Object> tags() {
        return failure.tags();
    }

    public interface Builder {

        public AppErrorException.Builder failure(Failure failure);

        public AppErrorException.Builder cause(Throwable thr);

        public AppErrorException.Builder errno(Errno errno);

        public AppErrorException.Builder tag(String tag, Object value);

        public AppErrorException.Builder tags(Map<String, Object> tags);

        public AppErrorException build();

        public void raise() throws AppErrorException;

    }
}
