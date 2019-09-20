/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.simple;

import fa.gs.utils.result.utils.Failure;

/**
 *
 * @author Fabio A. González Sosa
 * @param <S> Parametro de tipo para valor de exito.
 */
public interface DeferredResult<S> extends fa.gs.utils.result.DeferredResult<S, Failure> {

    /**
     * Callback para resoluciones de valores de exito.
     *
     * @param <S> Parametro de tipo para valor de exito.
     */
    interface OnSuccessCallback<S> extends fa.gs.utils.result.DeferredResult.OnSuccessCallback<S> {
    }

    /**
     * Callback para resoluciones de valores de fallo.
     */
    interface OnFailureCallback extends fa.gs.utils.result.DeferredResult.OnFailureCallback<Failure> {
    }

}
