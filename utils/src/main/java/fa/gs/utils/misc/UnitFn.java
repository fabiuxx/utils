/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
@FunctionalInterface
public interface UnitFn<T> {

    T execute() throws Throwable;

}
