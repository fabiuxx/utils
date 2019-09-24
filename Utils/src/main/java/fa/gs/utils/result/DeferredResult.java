/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result;

/**
 *
 * @author Fabio A. González Sosa
 * @param <S> Parametro de tipo para valores de exito.
 * @param <F> Parametro de tipo para valores de fallo.
 */
public interface DeferredResult<S, F> extends Result<S, F> {

    /**
     * Indica si el resultado esta pendiente de resolucion.
     *
     * @return {@code true} si el resultado de una operacion ya fue resuelta,
     * caso contrario {@code false}.
     */
    public boolean isPending();

    /**
     * Permite resolver un resultado exitoso.
     *
     * @param value Valor de resultado exitoso.
     */
    public void resolve(S value);

    /**
     * Permite resolver un resultado fallido.
     *
     * @param value Valor de resultado fallido.
     */
    public void reject(F value);

    /**
     * Establece el callback a invocar cuando se resuelve un resultado de exito.
     *
     * @param callback Callback.
     */
    public void onSuccess(DeferredResult.OnSuccessCallback<S> callback);

    /**
     * Establece el callback a invocar cuando se resuelve un resultado de fallo.
     *
     * @param callback Callback.
     */
    public void onFailure(DeferredResult.OnFailureCallback<F> callback);

    /**
     * Callback simple.
     *
     * @param <S> Parametro de tipo para resultados de exito.
     */
    interface OnSuccessCallback<S> {

        /**
         * Accion a realizar cuando se resuelve un valor de exito como resultado
         * de una operacion.
         *
         * @param result Valor de exito resuelto.
         */
        void onSuccess(final S result);

    }

    /**
     * Callback simple.
     *
     * @param <F> Parametro de tipo para resultados de fallo.
     */
    interface OnFailureCallback<F> {

        /**
         * Accion a realizar cuando se resuelve un valor de fallo como resultado
         * de una operacion.
         *
         * @param result Valor de fallo resuelto.
         */
        void onFailure(final F result);

    }
}
