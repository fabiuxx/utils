/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.utils;

import fa.gs.utils.misc.errors.Errno;
import java.io.Serializable;
import java.util.Map;

/**
 * Clase que abstrae las particularidades comunes de un error producido durante
 * la ejecucion de alguna operacion.
 *
 * @author Fabio A. González Sosa
 */
public final class Failure extends Failure_Attributes implements Serializable {

    /**
     * Constructor.
     *
     * @param message Mensaje de error para el error producido.
     * @param cause Excepcion generada como causa de un error producido.
     * @param errno Código numérico que mapea el error producido.
     * @param tags Coleccion de objetos asociados que sirven como contexto para
     * entender el error producido.
     */
    Failure(String message, Throwable cause, Errno errno, Map<String, Object> tags) {
        this.message = message;
        this.cause = cause;
        this.errno = errno;
        this.tags = tags;
    }

    /**
     * Obtiene un nuevo constructor para instancias de esta clase.
     *
     * @return Constructor para instancias de esta clase.
     */
    public static Failure.Builder builder() {
        return new Failure_Builder();
    }

    /**
     * Obtiene el valor de {@link #message message}.
     *
     * @return Texto.
     */
    public String message() {
        return message;
    }

    /**
     * Obtiene el valor de {@link #cause cause}.
     *
     * @return Excepcion.
     */
    public Throwable cause() {
        return cause;
    }

    /**
     * Obtiene el valor de {@link #errno errno}.
     *
     * @return Código numérico.
     */
    public Errno errno() {
        return errno;
    }

    /**
     * Obtiene la coleccion de {@link #tags tags}.
     *
     * @return Coleccion de tags.
     */
    public Map<String, Object> tags() {
        return tags;
    }

    /**
     * Constructor para esta clase.
     */
    public interface Builder extends Failure_Builder_Methods<Failure.Builder> {

        Failure build();

    }
}
