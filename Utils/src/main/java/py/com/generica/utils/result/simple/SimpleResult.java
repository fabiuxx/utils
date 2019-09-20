/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.result.simple;

import py.com.generica.utils.misc.errors.AppErrorException;
import py.com.generica.utils.misc.errors.Errors;
import py.com.generica.utils.result.BaseResult;
import py.com.generica.utils.result.utils.Failure;
import py.com.generica.utils.result.utils.Value;

/**
 *
 * @author Fabio A. González Sosa
 */
final class SimpleResult<T> extends BaseResult<T, Failure> implements Result<T> {

    /**
     * Constructor.
     *
     * @param valueSuccess Valor de exito.
     * @param valueFailure Valor de fallo.
     */
    SimpleResult(Value<T> valueSuccess, Value<Failure> valueFailure) {
        super(valueSuccess, valueFailure);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public T value() {
        return value(null);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public T value(T fallback) {
        return valueSuccess(fallback);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Failure failure() {
        return valueFailure();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void raise() throws AppErrorException {
        raise(false);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void raise(boolean raiseIfNoValue) throws AppErrorException {
        if (isFailure()) {
            Failure failure = failure();
            AppErrorException cause = Errors.failure(failure);
            Errors.popStackTrace(cause);
            throw cause;
        }

        if (raiseIfNoValue && !isSuccess()) {
            AppErrorException cause = Errors.builder()
                    .message("Se esperaba un resultado no nulo")
                    .build();
            Errors.popStackTrace(cause);
            throw cause;
        }
    }

}
