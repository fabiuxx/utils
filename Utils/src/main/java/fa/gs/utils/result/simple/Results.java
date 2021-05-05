/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.simple;

import fa.gs.utils.misc.errors.Errno;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.result.BaseResult;
import fa.gs.utils.result.utils.Failure;
import fa.gs.utils.result.utils.Failure_Builder_Methods;
import fa.gs.utils.result.utils.Value;
import fa.gs.utils.result.utils.Value_Builder_Methods;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Results {

    /**
     * Obtiene una instancia de resultado diferido para alguna operacion.
     *
     * @param <S> Parametro de tipo para valor de exito.
     * @return Resultado diferido.
     */
    public static <S> DeferredResult<S> deferred() {
        return new SimpleDeferredResult<>();
    }

    /**
     * Obtiene una instancia de resultado, con valores arbitrarios de exito y
     * fallo respectivamente.
     *
     * @param <S> Parametro de tipo para valores de exito.
     * @param <F> Parametro de tipo para valores de fallo.
     * @param successValue Valor de exito.
     * @param failureValue Valor de fallo.
     * @return
     */
    public static <S, F> BaseResult<S, F> simple(S successValue, F failureValue) {
        Value<S> s = Value.builder().value(successValue).build();
        Value<F> f = Value.builder().value(failureValue).build();
        return new BaseResult<>(s, f);
    }

    /**
     * Permite iniciar la construccion del resultado concreto para una operacion
     * exitosa.
     *
     * @param <S> Parámetro de tipo para valor de exito.
     * @return Constructor de resultados de exito.
     */
    public static <S> ResultOKBuilder<S> ok() {
        return Builder.instance(BuilderKind.FOR_RESULT_OK);
    }

    /**
     * Permite iniciar la construccion del resultado concreto para una operacion
     * fallida.
     *
     * @param <S> Parámetro de tipo para valor de exito.
     * @return Constructor de resultados de fallo.
     */
    public static <S> ResultKOBuilder<S> ko() {
        return Builder.instance(BuilderKind.FOR_RESULT_KO);
    }

    /**
     * Obtiene el valor encapsulado dentro de un resultado o lanza una excepcion
     * si el resultado representa un fallo.
     *
     * @param <S> Parametro de tipo.
     * @param result Resultado de una operacion.
     * @return Valor de resultado en caso de extio.
     * @throws Throwable Excepcion si el resultado representa un caso de fallo.
     */
    public static <S> S valueOrRaise(Result<S> result) throws Throwable {
        // Lanzar excepcion si el resultado no es de exito.
        try {
            result.raise();
        } catch (Throwable thr) {
            Errors.dump(System.err, result.failure().cause(), result.failure().message());
            throw thr;
        }

        // Obtener valor.
        return result.value();
    }

    /**
     * Obtiene el valor encapsulado dentro de un resultado o lanza una excepcion
     * si el resultado representa un fallo.
     *
     * @param <S> Parametro de tipo.
     * @param result Resultado de una operacion.
     * @param fallback Valor alternativo.
     * @return Valor de resultado en caso de extio.
     * @throws Throwable Excepcion si el resultado representa un caso de fallo.
     */
    public static <S> S valueOrRaise(Result<S> result, S fallback) throws Throwable {
        // Lanzar excepcion si el resultado no es de exito.
        try {
            result.raise();
        } catch (Throwable thr) {
            Errors.dump(System.err, result.failure().cause(), result.failure().message());
            throw thr;
        }

        // Obtener valor.
        return result.value(fallback);
    }

    /**
     * Obtiene el valor encapsulado dentro de un resultado o lanza una excepcion
     * si el resultado es nulo o si el resultado en general representa un fallo.
     *
     * @param <S> Parametro de tipo.
     * @param result Resultado de una operacion.
     * @return Valor de resultado en caso de extio.
     * @throws Throwable Excepcion si el resultado representa un caso de fallo.
     */
    public static <S> S valueNotEmptyOrRaise(Result<S> result) throws Throwable {
        // Lanzar excepcion si valor es vacio o si el resultado no es de exito.
        try {
            result.raise(true);
        } catch (Throwable thr) {
            Errors.dump(System.err, result.failure().cause(), result.failure().message());
            throw thr;
        }

        // Obtener valor no vacio.
        return result.value();
    }

    /**
     * Constructor general para resultados tanto de exito como de fallo.
     *
     * @param <S> Parámetro de tipo para valor de exito.
     */
    private static class Builder<S> implements ResultOKBuilder<S>, ResultKOBuilder<S> {

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        /**
         * Constructor del valor de exito.
         */
        private final Value.Builder<S> successBuilder;

        /**
         * Constructor del valor de fallo.
         */
        private final Failure.Builder failureBuilder;

        /**
         * Tipo de resultado a construir.
         */
        private final BuilderKind kind;
        //</editor-fold>

        /**
         * Constructor.
         *
         * @param kind Tipo de resultado a construir.
         */
        private Builder(BuilderKind kind) {
            this.kind = kind;
            this.successBuilder = Value.builder();
            this.failureBuilder = Failure.builder();
        }

        /**
         * Inicializador estatico.
         *
         * @param <T> Parámetro de tipo.
         * @param kind Tipo de resultado a construir.
         * @return Instancia de esta misma clase.
         */
        private static <T> Builder<T> instance(BuilderKind kind) {
            return new Builder<>(kind);
        }

        @Override
        @Deprecated
        public Builder<S> nullable(boolean nullable) {
            if (nullable) {
                strict(false);
            } else {
                strict(true);
            }
            return this;
        }

        @Override
        public Builder<S> strict(boolean strict) {
            successBuilder.strict(strict);
            return this;
        }

        @Override
        public Builder<S> value(S value) {
            successBuilder.value(value);
            return this;
        }

        @Override
        public Builder<S> cause(Throwable cause) {
            failureBuilder.cause(cause);
            return this;
        }

        @Override
        public Builder<S> message(String fmt, Object... args) {
            failureBuilder.message(fmt, args);
            return this;
        }

        @Override
        public Builder<S> errno(Errno errno) {
            failureBuilder.errno(errno);
            return this;
        }

        @Override
        public Builder<S> tag(String name, Object value) {
            failureBuilder.tag(name, value);
            return this;
        }

        @Override
        public Builder<S> tags(Map<String, Object> tags) {
            failureBuilder.tags(tags);
            return this;
        }

        @Override
        public Result<S> build() {
            Value<S> s;
            Value<Failure> f;

            switch (kind) {
                case FOR_RESULT_KO:
                    f = Value.builder().value(failureBuilder.build()).build();
                    s = Value.builder().value(null).build();
                    break;
                case FOR_RESULT_OK:
                    f = Value.builder().value(null).build();
                    s = successBuilder.build();
                    break;
                default:
                    f = Value.builder().value(null).build();
                    s = Value.builder().value(null).build();
                    break;
            }

            Result<S> result = new SimpleResult<>(s, f);
            return result;
        }
    }

    /**
     * Interface para constructor de respuestas simples, donde se espera algun
     * valor de exito de tipo {@code S} o bien un valor de fallo de tipo
     * {@link fa.gs.utils.result.utils.Failure failure}.
     *
     * @param <S> Parámetro de tipo para valor de exito.
     */
    public interface ResultBuilder<S> {

        /**
         * Construye una nueva instancia de
         * {@link fa.gs.utils.result.simple.Result result}.
         *
         * @param <S> Parámetro de tipo.
         * @return Nueva instancia de
         * {@link fa.gs.utils.result.simple.Result result}.
         */
        public <S> Result<S> build();

    }

    /**
     * Interface para constructor de respuestas a operaciones exitosas.
     *
     * @param <T> Parámetro de tipo.
     */
    public interface ResultOKBuilder<T> extends ResultBuilder<T>, Value_Builder_Methods<T, ResultOKBuilder<T>> {
    }

    /**
     * Interface para constructor de respuestas a operaciones fallidas.
     *
     * @param <T> Parámetro de tipo.
     */
    public interface ResultKOBuilder<T> extends ResultBuilder<T>, Failure_Builder_Methods<ResultKOBuilder<T>> {
    }

    /**
     * Enumeracion para los tipos de respuesta que pueden construirse con
     * {@link fa.gs.utils.result.simple.Results.Builder Builder}.
     */
    private enum BuilderKind {
        FOR_RESULT_KO,
        FOR_RESULT_OK;
    }

}
