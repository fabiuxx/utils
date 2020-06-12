/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.utils;

import fa.gs.utils.misc.Assertions;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo para valor almacenado.
 */
public final class Value<T> extends Value_Attributes<T> implements Serializable {

    /**
     * Constructor.
     *
     * @param value Valor encapsulado.
     * @param nullable Si se aceptan valores nulos como validos.
     */
    Value(T value, boolean strict) {
        this.strict = strict;
        this.value = value;
    }

    /**
     * Obtiene un nuevo constructor para instancias de esta clase.
     *
     * @param <T> Parametro de tipo.
     * @return Constructor para instancias de esta clase.
     */
    public static <T> Value.Builder<T> builder() {
        return new Value_Builder<>();
    }

    /**
     * Indica si existe algun valor almacenado.
     *
     * @return {@code true} si el resultado acepta valores nulos o bien el valor
     * almacenado es no nulo (habiendo especificado esta restriccion). Caso
     * contrario {@code false}.
     */
    public boolean hasValue() {
        // Si se admiten valores nulos, no aplicar ningun control.
        if (!strict) {
            return true;
        }

        if (strict) {
            // No se admiten valores nulos.
            if (value == null) {
                return false;
            }

            // Si es un array, debe tener algun elemento.
            if (value.getClass().isArray()) {
                Object[] array = (Object[]) value;
                if (Assertions.isNullOrEmpty(array)) {
                    return false;
                }
            }

            // Si es una coleccion, debe tener algun elemento.
            if (value instanceof Collection) {
                Collection collection = (Collection) value;
                if (Assertions.isNullOrEmpty(collection)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Obtiene el valor almacenado.
     *
     * @return Valor almacenado, si hubiere.
     */
    public T get() {
        return get(null);
    }

    /**
     * Obtiene el valor almacenado.
     *
     * @param fallback Valor a retornar en caso de que no exista un valor
     * almacenado.
     * @return Valor.
     */
    public T get(T fallback) {
        if (hasValue()) {
            return value;
        } else {
            return fallback;
        }
    }

    /**
     * Constructor para esta clase.
     *
     * @param <T> Parámetro de tipo.
     */
    public interface Builder<T> extends Value_Builder_Methods<T, Value.Builder<T>> {

        /**
         * Construye una instancia de
         * {@link fa.gs.utils.result.utils.Value ResultValue}.
         *
         * @param <T> Parametro de tipo.
         * @return Instancia de
         * {@link fa.gs.utils.result.utils.Value ResultValue}.
         */
        public <T> Value<T> build();

    }

}
