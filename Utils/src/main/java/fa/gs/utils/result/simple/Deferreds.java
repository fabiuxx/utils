/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.simple;

import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.result.utils.Failure;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Deferreds {

    public static final DeferredResult<Boolean> OK() {
        DeferredResult<Boolean> result = Results.deferred();
        result.resolve(Boolean.TRUE);
        return result;
    }

    public static final DeferredResult<Boolean> KO() {
        DeferredResult<Boolean> result = Results.deferred();
        result.resolve(Boolean.FALSE);
        return result;
    }

    public static final DeferredResult<Void> resolve(DeferredResult<Void> result) {
        if (result.isPending()) {
            result.resolve(null);
        }
        return result;
    }

    public static final <S> DeferredResult<S> resolve(DeferredResult<S> result, S value) {
        if (result.isPending()) {
            result.resolve(value);
        }
        return result;
    }

    public static final <S> DeferredResult<S> reject(DeferredResult<S> result, String fmt, Object... args) {
        if (result.isPending()) {
            Failure failure = Failure.builder().message(fmt, args).build();
            Errors.dump(System.err, new Exception(failure.message(), failure.cause()));
            result.reject(failure);
        }
        return result;
    }

    public static final <S> DeferredResult<S> reject(DeferredResult<S> result, Throwable thr) {
        if (result.isPending()) {
            Errors.dump(System.err, thr);
            Failure failure = Failure.builder().cause(thr).build();
            result.reject(failure);
        }
        return result;
    }

    public static final <S> DeferredResult<S> reject(DeferredResult<S> result, Throwable thr, String fmt, Object... args) {
        if (result.isPending()) {
            Failure failure = Failure.builder().cause(thr).message(fmt, args).build();
            Errors.dump(System.err, new Exception(failure.message(), failure.cause()));
            result.reject(failure);
        }
        return result;
    }

    public static final <S> DeferredResult<S> reject(DeferredResult<S> result, Failure failure) {
        if (result.isPending()) {
            result.reject(failure);
        }
        return result;
    }

}
