/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.simple;

import fa.gs.utils.misc.errors.AppErrorException;
import fa.gs.utils.result.utils.Failure;

/**
 *
 * @author Fabio A. González Sosa
 * @param <S> Parametro de tipo para valores de exito.
 */
public interface Result<S> extends fa.gs.utils.result.Result<S, Failure> {

    /**
     * Obtiene el valor de exito que fue resuelto.
     *
     * @return Valor de exito.
     */
    public S value();

    /**
     * Obtiene el valor de exito que fue resuelto.
     *
     * @param fallback Valor alternativo a retornar en caso de que no exista un
     * valor de exito resuelto.
     * @return Valor de exito.
     */
    public S value(S fallback);

    /**
     * Obtiene el valor de fallo.
     *
     * @return Valor de fallo.
     */
    public Failure failure();

    /**
     * Permite lanzar una excepcion, si el resultado es especificamente de
     * fallo.
     *
     * @throws AppErrorException Error de resultado.
     */
    public void raise() throws AppErrorException;

    /**
     * Permite lanzar una ena excepcion si el resultado es de fallo o si el
     * resultado no es de fallo pero no posee valor.
     *
     * @param raiseIfNoValue Indica si se debe lanzar una excepcion si, en caso
     * de no ser un resultado de fallo, el valor del resultado es nulo.
     * @throws AppErrorException Error de resultado.
     */
    public void raise(boolean raiseIfNoValue) throws AppErrorException;

}
