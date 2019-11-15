/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import java.lang.reflect.Field;

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

    public static boolean set(Object obj, String attribute, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(attribute);
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (Throwable thr) {
            return false;
        }
    }

}
