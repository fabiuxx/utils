/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.simple;

import fa.gs.utils.misc.errors.AppErrorException;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.result.BaseResult;
import fa.gs.utils.result.utils.Failure;
import fa.gs.utils.result.utils.Value;

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
    public boolean isSuccess() {
        return (valueSuccess != null && valueSuccess.hasValue());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isFailure() {
        return (valueFailure != null && valueFailure.hasValue());
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
