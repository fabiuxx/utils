/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import py.com.generica.utils.misc.errors.Errors;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Assertions {

    /**
     * Verifica que dos objetos sean iguales.
     *
     * @param a Objeto a.
     * @param b Objeto b.
     * @return {@code true} si los objetos son iguales, caso contrario
     * {@code false}.
     */
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * Verifica si un objeto es nulo y emite una excepcion si la condicion se
     * cumple.
     *
     * @param object Objeto a verificar.
     * @throws NullPointerException Excepcion lanzada.
     */
    public static void raiseIfNull(Object object) throws IllegalArgumentException {
        if (object == null) {
            IllegalArgumentException npe = new IllegalArgumentException();
            Errors.popStackTrace(npe, 1);
            throw npe;
        }
    }

    /**
     * Verifica si un objeto es nulo.
     *
     * @param object Objeto a verificar.
     * @return {@code true} si el objeto es nulo. {@code false} caso contrario.
     */
    public static boolean isNull(Object object) {
        return (object == null);
    }

    /**
     * Verifica si un objeto es no nulo.
     *
     * @param object Objeto a verificar.
     * @return {@code true} si el objeto es no nulo. {@code false} caso
     * contrario.
     */
    public static boolean notNull(Object object) {
        return (object != null);
    }

    /**
     * Verifica si una cadena de texto es nula o vacía.
     *
     * @param value Cadena de texto.
     * @return {@code true}, si la cadena es vacia o nula. {@code false} en caso
     * contrario.
     */
    public static boolean stringNullOrEmpty(String value) {
        return (value == null || value.isEmpty());
    }

    /**
     * Verifica que un entero supere un valor minimo.
     *
     * @param value Entero a verificar.
     * @param min Valor minimo.
     * @return {@code true}, si el entero es mayor que el valor minimo.
     * {@code false}, caso contrario.
     */
    public static boolean integerMinValue(Integer value, Integer min) {
        return (value >= min);
    }

    /**
     * Verifica que un entero este en el rango [min, max].
     *
     * @param value Entenro a verificar.
     * @param min Valor minimo.
     * @param max Valor maximo.
     * @return {@code true}, si el entero pertenece al rango. {@code false} en
     * caso contrario.
     */
    public static boolean integerInRange(Integer value, Integer min, Integer max) {
        return (value >= min) && (value <= max);
    }

    /**
     * Verifica si una cadena representa una direccion de correo.
     *
     * @param email Cadena de texto.
     * @return {@code true} si la cadena es una direccion de correo,
     * {@code false} caso contrario.
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$");
        Matcher matcher = pattern.matcher((email));
        return matcher.matches();
    }

    /**
     * Verifica si una cadena representa un valor numerico entero.
     *
     * @param value Cadena de texto.
     * @return {@code true} si la cadena es una valor numerico entero,
     * {@code false} caso contrario.
     */
    public static boolean isInteger(String value) {
        try {
            BigInteger bi = new BigInteger(value);
            return true;
        } catch (Throwable thr) {
            return false;
        }
    }

    /**
     * Verifica si una cadena representa un valor numerico entero positivo.
     *
     * @param value Cadena de texto.
     * @return {@code true} si la cadena es una valor numerico entero,
     * {@code false} caso contrario.
     */
    public static boolean isPositiveInteger(String value) {
        try {
            Long number = Long.valueOf(value);
            return number > 0L;
        } catch (Throwable thr) {
            return false;
        }
    }

    /**
     * Verifica si un valor numerico representa un valor entero positivo.
     *
     * @param value Valor numerico.
     * @return {@code true} si el numero es un entero positivo, {@code false}
     * caso contrario.
     */
    public static boolean isPositiveInteger(Number value) {
        try {
            return value.longValue() > 0L;
        } catch (Throwable thr) {
            return false;
        }
    }

    /**
     * Verifica si una cadena representa un valor numerico decimal.
     *
     * @param value Cadena de texto.
     * @return {@code true} si la cadena es una valor numerico entero o decimal,
     * {@code false} caso contrario.
     */
    public static boolean isDecimal(String value) {
        try {
            Double.valueOf(value);
            return true;
        } catch (Throwable thr) {
            return false;
        }
    }

    /**
     * Verifica si una cadena representa un valor numerico decimal positivo.
     *
     * @param value Cadena de texto.
     * @return {@code true} si la cadena es una valor numerico entero o decimal,
     * {@code false} caso contrario.
     */
    public static boolean isPositiveDecimal(String value) {
        try {
            Double number = Double.valueOf(value);
            return number > 0.0;
        } catch (Throwable thr) {
            return false;
        }
    }

    /**
     * Verifica si un valor numerico representa un valor decimal positivo.
     *
     * @param value Valor numerico.
     * @return {@code true} si el numero es un decimal positivo, {@code false}
     * caso contrario.
     */
    public static boolean isPositiveDecimal(Number value) {
        try {
            return value.doubleValue() > 0.0;
        } catch (Throwable thr) {
            return false;
        }
    }

    /**
     * Verifica si una coleccion de elementos es nula o esta vacia.
     *
     * @param collection Collection de datos.
     * @return {@code true} si la coleccion es nula o vacia, {@code false} caso
     * contrario.
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Verifica si una coleccion de elementos es nula o esta vacia.
     *
     * @param collection Collection de datos.
     * @return {@code true} si la coleccion es nula o vacia, {@code false} caso
     * contrario.
     */
    public static boolean isNullOrEmpty(Map<?, ?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Verifica si una coleccion de elementos es nula o esta vacia.
     *
     * @param collection Collection de datos.
     * @return {@code true} si la coleccion es nula o vacia, {@code false} caso
     * contrario.
     */
    public static <T> boolean isNullOrEmpty(T[] collection) {
        return (collection == null || collection.length == 0);
    }

}
