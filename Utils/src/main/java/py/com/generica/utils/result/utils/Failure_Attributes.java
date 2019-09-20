/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.result.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
class Failure_Attributes implements Serializable {

    /**
     * Mensaje descriptivo para el error producido.
     */
    protected String message;

    /**
     * Excepcion generada como causa de un error producido.
     */
    protected Throwable cause;

    /**
     * Código numérico que mapea el error producido.
     */
    protected int errno;

    /**
     * Coleccion de objetos asociados que sirven como contexto para entender el
     * error producido.
     */
    protected Map<String, Object> tags;

    /**
     * Constructor.
     */
    public Failure_Attributes() {
        this.message = "";
        this.cause = null;
        this.errno = 0;
        this.tags = new HashMap<>();
    }

}
