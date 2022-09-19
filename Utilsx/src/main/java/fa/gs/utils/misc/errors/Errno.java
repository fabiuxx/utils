/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.errors;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Errno extends Serializable {

    /**
     * Cadena de tres caracteres alfanumericos.
     *
     * @return Cadena que describe el ambito del codigo de error o bien la
     * aplicación/libreria que lo genera.
     */
    public String getDescriptor();

    /**
     * Cadena de seis caracteres numericos.
     *
     * @return Cadena que describe el codigo concreto de error.
     */
    public String getCode();

    /**
     * Obtiene la cadena completa que identifica al codigo de error.
     *
     * @return Cadena de codigo de error.
     */
    public String getErrnoString();

}
