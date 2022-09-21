/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.annotations;

import fa.gs.utils.database.query.elements.Join;
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
@Repeatable(FgJoins.class)
public @interface FgJoin {

    /**
     * Tipo de join.
     *
     * @return Tipo de join.
     */
    Join.Type type() default Join.Type.NORMAL;

    /**
     * Nombre de tabla.
     *
     * @return Nombre de tabla.
     */
    String table();

    /**
     * Cuando este flag es <code>true</code>, el valor de tabla es interpretado
     * como una expresion y no como un nombre de tabla. Esto permite especificar
     * subquerys como tablas.
     *
     * @return {@code true} si debe utilizarse de forma literal y no como nombre
     * calificado de tabla.
     */
    boolean useRawTableDefinition() default false;

    /**
     * Alias para nombre o expresion de tabla.
     *
     * @return Alias para nombre o expresion de tabla.
     */
    String as();

    /**
     * Condicion de join.
     *
     * @return Condicion de join.
     */
    String on();

}
