/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.serialization;

import fa.gs.utils.misc.json.adapter.JsonAdapterFromJson;
import fa.gs.utils.misc.json.adapter.JsonAdapterToJson;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Fabio A. González Sosa
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonProperty {

    /**
     * Nombre para acceder a la propiedad Json.
     *
     * @return Nombre.
     */
    String name() default "";

    /**
     * Tipo de resolucion de propiedad. Si su existencia es mandatoria u
     * opcional.
     *
     * @return Tipo de resolucion.
     */
    JsonResolution resolution() default JsonResolution.MANDATORY;

    /**
     * Clase opcional que permite convertir la representacion de un elemento
     * json a un tipo concreto.
     *
     * @return Clase que implementa JsonConverter.
     */
    Class<? extends JsonAdapterFromJson> fromJsonAdapter() default JsonAdapterFromJson.class;

    /**
     * Clase opcional que permite convertir la representacion de un elemento
     * json a un tipo concreto.
     *
     * @return Clase que implementa JsonConverter.
     */
    Class<? extends JsonAdapterToJson> toJsonAdapter() default JsonAdapterToJson.class;

}
