/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.mixins;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Noop<T extends Noop<T>> {

    default T noop() {
        return (T) this;
    }

}
