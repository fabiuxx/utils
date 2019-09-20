/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.simple;

import fa.gs.utils.result.BaseDeferredResult;
import fa.gs.utils.result.utils.Failure;

/**
 *
 * @author Fabio A. González Sosa
 * @param <S> Parametro de tipo para valor de exito.
 */
public class SimpleDeferredResult<S> extends BaseDeferredResult<S, Failure> implements fa.gs.utils.result.simple.DeferredResult<S> {
}
