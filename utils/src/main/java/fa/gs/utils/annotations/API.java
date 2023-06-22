/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que permite incluir información adicional sobre elementos que
 * forman parte del API pública de una solución software.
 *
 * @author Fabio A. González Sosa
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface API {

    /**
     * Versión desde la cual la funcionalidad está presente.
     *
     * @return Versión.
     */
    String since() default "";

}
