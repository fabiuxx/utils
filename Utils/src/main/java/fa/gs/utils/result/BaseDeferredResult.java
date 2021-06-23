/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result;

import fa.gs.utils.result.utils.Value;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

/**
 *
 * @author Fabio A. González Sosa
 * @param <S> Parametro de tipo para valores de exito.
 * @param <F> Parametro de tipo para valores de fallo.
 */
public class BaseDeferredResult<S, F> extends BaseResult<S, F> implements DeferredResult<S, F> {

    /**
     * Objeto diferido que se utiliza para las notificaciones concretas de
     * resolucion de resultados.
     */
    private final DeferredObject<S, F, Void> deferred;

    /**
     * Constructor.
     */
    public BaseDeferredResult() {
        super(null, null);
        this.deferred = new DeferredObject<>();
    }

    @Override
    public final void onSuccess(final DeferredResult.OnSuccessCallback<S> callback) {
        if (callback != null) {
            deferred.done(callback::onSuccess);
        }
    }

    @Override
    public final void onFailure(final DeferredResult.OnFailureCallback<F> callback) {
        if (callback != null) {
            deferred.fail(callback::onFailure);
        }
    }

    @Override
    public void onFinally(final DeferredResult.OnFinallyCallback callback) {
        if (callback != null) {
            deferred.always((Promise.State state, S success, F failure) -> {
                callback.onFinally();
            });
        }
    }

    @Override
    public void onFinally(final DeferredResult.OnFinallyCallback2<S, F> callback) {
        if (callback != null) {
            deferred.always((Promise.State state, S success, F failure) -> {
                callback.onFinally(success, failure);
            });
        }
    }

    @Override
    public void resolve(S value) {
        if (isPending()) {
            valueSuccess = Value.builder().value(value).build();
            deferred.resolve(value);
        }
    }

    @Override
    public void reject(F value) {
        if (isPending()) {
            valueFailure = Value.builder().value(value).build();
            deferred.reject(value);
        }
    }

    @Override
    public boolean isSuccess() {
        return deferred.isResolved();
    }

    @Override
    public boolean isFailure() {
        return deferred.isRejected();
    }

    @Override
    public boolean isPending() {
        return deferred.isPending();
    }

    @Override
    public S valueSuccess(S fallback) {
        if (isPending()) {
            throw new IllegalStateException("El resultado aún no ha sido resuelto.");
        }
        return valueSuccess.get(fallback);
    }

    @Override
    public F valueFailure(F fallback) {
        if (isPending()) {
            throw new IllegalStateException("El resultado aún no ha sido resuelto.");
        }
        return valueFailure.get(fallback);
    }

}
