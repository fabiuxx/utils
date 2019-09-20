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
class AppErrorExceptionBuilder implements AppErrorException.Builder {

    private final Failure.Builder builder;

    public AppErrorExceptionBuilder() {
        this.builder = Failure.builder();
    }

    @Override
    public AppErrorException.Builder failure(Failure failure) {
        if (failure != null) {
            this.builder.cause(failure.cause());
            this.builder.message(failure.message());
            this.builder.errno(failure.errno());
            this.builder.tags(failure.tags());
        }
        return this;
    }

    @Override
    public AppErrorException.Builder cause(Throwable thr) {
        builder.cause(thr);
        return this;
    }

    @Override
    public AppErrorException.Builder message(String fmt, Object... args) {
        builder.message(fmt, args);
        return this;
    }

    @Override
    public AppErrorException.Builder errno(int errno) {
        builder.errno(errno);
        return this;
    }

    @Override
    public AppErrorException.Builder tag(String tag, Object value) {
        builder.tag(tag, value);
        return this;
    }

    @Override
    public AppErrorException.Builder tags(Map<String, Object> tags) {
        builder.tags(tags);
        return this;
    }

    @Override
    public AppErrorException build() {
        Failure failure = builder.build();
        return new AppErrorException(failure);
    }

    @Override
    public void raise() throws AppErrorException {
        AppErrorException ex = build();
        throw ex;
    }

}
