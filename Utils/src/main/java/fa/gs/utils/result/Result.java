/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 * @param <S> Parametro de tipo para valores de exito.
 * @param <F> Parametro de tipo para valores de fallo.
 */
public interface Result<S, F> extends Serializable {

    /**
     * Indica si el resultado concreto de la operacion es un valor de exito.
     *
     * @return {@code true} si existe algun valor valido como resultado de caso
     * de exito. Caso contrario {@code false}.
     */
    public boolean isSuccess();

    /**
     * Indica si el resultado concreto de la operacion es un valor de fallo.
     *
     * @return {@code true} si existe algun valor valido como resultado de caso
     * de fallo. Caso contrario {@code false}.
     */
    public boolean isFailure();

    /**
     * Valor almacenado como resultado de una operacion exitosa.
     *
     * @return Objeto.
     */
    public S valueSuccess();

    /**
     * Obtiene el valor almacenado como resultado de una operacion exitosa.
     *
     * @param fallback Valor alternativo en caso de que no exista un valor
     * concreto para el caso de exito.
     * @return Objeto.
     */
    public S valueSuccess(S fallback);

    /**
     * Obtiene el valor almacenado como resultado de una operacion fallida.
     *
     * @return Objeto.
     */
    public F valueFailure();

    /**
     * Obtiene el valor almacenado como resultado de una operacion fallida.
     *
     * @param fallback Valor alternativo en caso de que no exista un valor
     * concreto para el caso de fallo.
     * @return Objeto.
     */
    public F valueFailure(F fallback);

}
