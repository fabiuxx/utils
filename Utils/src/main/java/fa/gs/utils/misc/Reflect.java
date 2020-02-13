/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import sun.reflect.ReflectionFactory;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Reflect {

    /**
     * Obtiene el valor de un atributo dentro de un objeto.
     *
     * @param <T> Parametro de tipo de atributo.
     * @param obj Objeto a procesar.
     * @param attribute Nombre de atributo a verificar via reflexion.
     * @param type Tipo de atributo.
     * @return Valor de atributo, si hubiere. Caso contrario {@code null}.
     */
    public static <T> T get(Object obj, String attribute, Class<T> type) {
        try {
            Field field = obj.getClass().getDeclaredField(attribute);
            return get(obj, field, type);
        } catch (Throwable thr) {
            return null;
        }
    }

    /**
     * Obtiene el valor de un atributo dentro de un objeto.
     *
     * @param <T> Parametro de tipo de atributo.
     * @param obj Objeto a procesar.
     * @param field Atributo a verificar via reflexion.
     * @param type Tipo de atributo.
     * @return Valor de atributo, si hubiere. Caso contrario {@code null}.
     */
    public static <T> T get(Object obj, Field field, Class<T> type) {
        try {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value == null) {
                return null;
            }
            return type.cast(value);
        } catch (Throwable thr) {
            return null;
        }
    }

    /**
     * Establece el valor de un atributo dentro de un objeto.
     *
     * @param obj Objeto a procesar.
     * @param attribute Nombre de atributo.
     * @param value Valor para atributo.
     * @return {@code true} si la asignacion tuvo exito, caso contrario
     * {@code false}.
     */
    public static boolean set(Object obj, String attribute, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(attribute);
            return set(obj, field, value);
        } catch (Throwable thr) {
            return false;
        }
    }

    /**
     * Establece el valor de un atributo dentro de un objeto.
     *
     * @param obj Objeto a procesar.
     * @param field Atributo.
     * @param value Valor para atributo.
     * @return {@code true} si la asignacion tuvo exito, caso contrario
     * {@code false}.
     */
    public static boolean set(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static <T> T getAnnotation(Class klass, Class<T> annotationKlass) {
        return getAnnotation(klass.getAnnotations(), annotationKlass);
    }

    public static <T> T getAnnotation(Field field, Class<T> annotationKlass) {
        return getAnnotation(field.getAnnotations(), annotationKlass);
    }

    public static <T> T getAnnotation(Method method, Class<T> annotationKlass) {
        return getAnnotation(method.getAnnotations(), annotationKlass);
    }

    private static <T> T getAnnotation(Annotation[] annotations, Class<T> annotationKlass) {
        for (Annotation an : annotations) {
            if (an.annotationType().equals(annotationKlass)) {
                return annotationKlass.cast(an);
            }
        }
        return null;
    }

    public static Object createInstance(Class klass) throws Throwable {
        try {
            // Metodo 1.
            Constructor defaultConstructor = klass.getConstructor();
            defaultConstructor.setAccessible(true);
            return defaultConstructor.newInstance();
        } catch (Throwable thr) {
            // Metodo 2.
            return klass.newInstance();
        }
    }

    public static Object createInstanceUnsafe(Class klass) throws Throwable {
        ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
        java.lang.reflect.Constructor ctor = Object.class.getDeclaredConstructor();
        java.lang.reflect.Constructor intConstr = rf.newConstructorForSerialization(klass, ctor);
        return klass.cast(intConstr.newInstance());
    }

}
