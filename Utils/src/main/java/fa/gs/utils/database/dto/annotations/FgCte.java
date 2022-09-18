/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.annotations;

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
@Repeatable(FgCtes.class)
public @interface FgCte {

    /**
     * Nombre concreto del CTE.
     *
     * @return Nombre de CTE.
     */
    String name() default "";

    /**
     * Cuerpo de CTE, sin posibilidad de parametrizaciones.
     * @return Cuerpo de CTE.
     */
    String body() default "";

    /**
     * Indica si la CTE es de tipo recursiva.
     * @return {@code true} si el CTE es de naturaleza recursiva, caso contrario {@code false}.
     */
    boolean recursive() default false;

}
