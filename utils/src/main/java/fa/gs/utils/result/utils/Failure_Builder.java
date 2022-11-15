/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.utils;

import fa.gs.utils.misc.errors.AppErrorException;
import fa.gs.utils.misc.errors.Errno;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
class Failure_Builder extends Failure_Attributes implements Failure.Builder {

    Failure_Builder() {
        super();
    }

    @Override
    public Failure.Builder cause(Throwable cause) {
        this.cause = cause;
        if (cause instanceof AppErrorException) {
            AppErrorException ex = (AppErrorException) cause;
            this.tags(ex.tags());
            this.errno(ex.errno());
        }
        return this;
    }

    @Override
    public Failure.Builder errno(Errno errno) {
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
        return new Failure(cause, errno, tags);
    }

}
