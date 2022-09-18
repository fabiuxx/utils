/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.annotations;

import fa.gs.utils.database.query.elements.Order;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Fabio A. González Sosa
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(FgOrderBys.class)
public @interface FgOrderBy {

    /**
     * Tipo de ordenacion.
     * @return Tipo de ordenacion.
     */
    Order.Type type() default Order.Type.ASC;

    /**
     * Valor a utilizar como criterio de ordenacion.
     * @return Valor a utilizar como criterio de ordenacion.
     */
    String value();

    /**
     * Indica si se hace referencia a una posicion de columna de la fila en
     * lugar de un nombre concreto de columna.
     *
     * @return {@code true} si el atributo {@link #value() value} hace
     * referencia a una proyeccion puntual o a la posicion de una proyeccion.
     */
    boolean positional() default false;

}
