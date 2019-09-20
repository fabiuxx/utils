/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.result;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.impl.DeferredObject;
import py.com.generica.utils.result.utils.Value;

/**
 *
 * @author Fabio A. González Sosa
 * @param <S> Parametro de tipo para valores de exito.
 * @param <F> Parametro de tipo para valores de fallo.
 */
public class BaseDeferredResult<S, F> extends BaseResult<S, F> implements DeferredResult<S, F> {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Objeto diferido que se utiliza para las notificaciones concretas de
     * resolucion de resultados.
     */
    private final DeferredObject<S, F, Void> deferred;
    //</1editor-fold>

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
            deferred.done(new DoneCallback<S>() {
                @Override
                public void onDone(S value) {
                    callback.onSuccess(value);
                }
            });
        }
    }

    @Override
    public final void onFailure(final DeferredResult.OnFailureCallback<F> callback) {
        if (callback != null) {
            deferred.fail(new FailCallback<F>() {
                @Override
                public void onFail(F value) {
                    callback.onFailure(value);
                }
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
