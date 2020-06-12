/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.utils;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo para valor almacenado.
 */
public class Value_Attributes<T> implements Serializable {

    /**
     * Indica si se debe aplicar un control estricto para determinar si el valor
     * contenido es valido o no.
     */
    protected boolean strict;

    /**
     * Valor encapsulado.
     */
    protected T value;

    /**
     * Constructor.
     */
    public Value_Attributes() {
        this.value = null;
        this.strict = false;
    }

}
