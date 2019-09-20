/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters;

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
public @interface Construction {

    /**
     * Indica la estrategia de construccion a utlizar.
     *
     * @return Estrategia de construccion.
     */
    Construction.Strategy strategy() default Construction.Strategy.NORMAL;

    /**
     * Enumeracion que representa la estrategia de construccion de instancias a
     * utilizar.
     */
    public static enum Strategy {
        /**
         * Construccion tradicional utilizando un constructor publico sin
         * parametros.
         */
        NORMAL,
        /**
         * Construccion no administrada (insegura) utilizando artificios del API
         * privada del SDK.
         */
        UNSAFE
    }

}
