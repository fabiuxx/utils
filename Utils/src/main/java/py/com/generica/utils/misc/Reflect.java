/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc;

import java.lang.reflect.Field;
import py.com.generica.utils.criteria.column.Column;

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
     * @param column Descripcion de atributo columna, en caso que el objeto sea
     * una entidad.
     * @return Valor de atributo, si hubiere. Caso contrario {@code null}.
     */
    public static <T> T get(Object obj, Column<T> column) {
        return get(obj, column.getName(), column.getType());
    }

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
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }

}
