/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result;

import fa.gs.utils.result.utils.Value;

/**
 *
 * @author Fabio A. González Sosa
 * @param <S> Parametro de tipo para valores de exito.
 * @param <F> Parametro de tipo para valores de fallo.
 */
public class BaseResult<S, F> implements Result<S, F> {

    /**
     * Valor de exito que se obtiene al ejecutar de manera exitosa alguna
     * operacion.
     */
    protected Value<S> valueSuccess;

    /**
     * Valor de fallo que se obtiene al ejecutar de manera fallida alguna
     * operacion.
     */
    protected Value<F> valueFailure;

    /**
     * Constructor.
     *
     * @param valueSuccess Valor de exito.
     * @param valueFailure Valor de fallo.
     */
    public BaseResult(Value<S> valueSuccess, Value<F> valueFailure) {
        this.valueSuccess = valueSuccess;
        this.valueFailure = valueFailure;
    }

    @Override
    public boolean isSuccess() {
        return (valueSuccess != null && valueSuccess.hasValue());
    }

    @Override
    public boolean isFailure() {
        return (valueFailure != null && valueFailure.hasValue());
    }

    @Override
    public S valueSuccess() {
        return valueSuccess(null);
    }

    @Override
    public S valueSuccess(S fallback) {
        return valueSuccess.get(fallback);
    }

    @Override
    public F valueFailure() {
        return valueFailure(null);
    }

    @Override
    public F valueFailure(F fallback) {
        return valueFailure.get(fallback);
    }

}
