/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Fabio A. González Sosa
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FgDto {

    /**
     * Nombre concreto de tabla. Puede ser un nombre cualificado completo,
     * ejemplo: {@code db.schema.table}.
     *
     * @return Nombre de tabla.
     */
    String table();

    /**
     * Indica un alias a utilizar en lugar del nombre concreto de la tabla. Este
     * alias es util cuando se realizan joins con otros tablas y existe una
     * posibilidad de colision de nombres para columnas/proyecciones.
     *
     * @return Alias de tabla.
     */
    String as() default "";

}
