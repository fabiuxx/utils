/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.mixins;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Noop<T extends Noop<T>> {

    default T noop() {
        return (T) this;
    }

}
