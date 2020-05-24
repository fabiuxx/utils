/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Stream;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Arrays {

    /**
     * Aplica una conversion de tipo a los elementos dentro de un array
     * generico.
     *
     * @param <T> parametro de tipo.
     * @param values Array con valores cuyo tipo se debea convertir.
     * @param type Tipo final al cual se convierten los elementos.
     * @return Array con conversion de tipo aplicado a cada elemento.
     */
    public static <T> T[] unwrap(Object[] values, Class<T> type) {
        if (Assertions.isNullOrEmpty(values)) {
            return null;
        }

        T[] values0 = Arrays.create(type, values.length);
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if (value == null) {
                values0[i] = null;
            } else {
                values0[i] = type.cast(value);
            }
        }
        return values0;
    }

    /**
     * Aplica una conversion de tipo a los elementos dentro de una coleccion
     * generica.
     *
     * @param <T> parametro de tipo.
     * @param values Coleccion con valores cuyo tipo se debea convertir.
     * @param type Tipo final al cual se convierten los elementos.
     * @return Array con conversion de tipo aplicado a cada elemento.
     */
    public static <T> T[] unwrap(Collection<T> values, Class<T> type) {
        Enumeration<T> enumeration = Collections.enumeration(values);
        ArrayList<T> list = Collections.list(enumeration);
        T[] array = (T[]) Array.newInstance(type, list.size());
        return list.toArray(array);
    }

    /**
     * Obtiene un objeto que permite aplicar operadores de flujo sobre un array
     * de elementos.
     *
     * @param <T> Parametro de tipo.
     * @param elements Array de elementos.
     * @return Stream de elementos.
     */
    public static <T> Stream<T> stream(T[] elements) {
        return Lists.wrap(elements).stream();
    }

    /**
     * Obtiene una representacion basica en forma de texto para un array de
     * elementos.
     *
     * @param <T> Parametro de tipo.
     * @param values Array de elementos.
     * @return Representacion en forma de texto.
     */
    public static <T> String toString(T[] values) {
        if (Assertions.isNullOrEmpty(values)) {
            return null;
        } else {
            return Joiner.of(values).join();
        }
    }

    /**
     * Crea un array de tipo y tamanho especificos mediante reflexion.
     *
     * @param <T> Parametro de tipo.
     * @param type Tipo concreto para elementos en array.
     * @param size Tamanho de array.
     * @return Array.
     */
    public static <T> T[] create(Class<T> type, int size) {
        T[] array = (T[]) Array.newInstance(type, size);
        return array;
    }

    @Deprecated
    public static <T> T[] array(Collection<T> collection, Class<T> type) {
        return unwrap(collection, type);
    }

    /**
     * Determina el tamanho de un array generico.
     *
     * @param <T> Parametro de tipo.
     * @param array Array.
     * @return Tamanho de array.
     */
    public static <T> int size(T[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Implementaciones de 'size' para tipos primitivos.">
    public static <T> int size(byte[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static <T> int size(boolean[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static <T> int size(char[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static <T> int size(short[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static <T> int size(int[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static <T> int size(long[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static <T> int size(float[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static <T> int size(double[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }
    //</editor-fold>

}
