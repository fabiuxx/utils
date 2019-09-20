/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.result.utils;

import java.util.Map;
import py.com.generica.utils.misc.Assertions;
import py.com.generica.utils.misc.errors.AppErrorException;

/**
 *
 * @author Fabio A. González Sosa
 */
class Failure_Builder extends Failure_Attributes implements Failure.Builder {

    private boolean overrideMessage;

    Failure_Builder() {
        super();
        this.overrideMessage = true;
    }

    @Override
    public Failure.Builder message(String fmt, Object... args) {
        if (overrideMessage) {
            this.message = String.format(fmt, args);
        }
        return this;
    }

    @Override
    public Failure.Builder cause(Throwable cause) {
        this.cause = cause;
        if (cause instanceof AppErrorException) {
            AppErrorException ex = (AppErrorException) cause;
            this.tags(ex.tags());
            this.errno(ex.errno());

            String msg = ex.getMessage();
            if (!Assertions.stringNullOrEmpty(msg)) {
                this.message(msg);
                this.overrideMessage = false;
            }
        }
        return this;
    }

    @Override
    public Failure.Builder errno(int errno) {
        this.errno = errno;
        return this;
    }

    @Override
    public Failure.Builder tag(String name, Object value) {
        this.tags.put(name, value);
        return this;
    }

    @Override
    public Failure.Builder tags(Map<String, Object> tags) {
        this.tags.putAll(tags);
        return this;
    }

    @Override
    public Failure build() {
        return new Failure(message, cause, errno, tags);
    }

}
