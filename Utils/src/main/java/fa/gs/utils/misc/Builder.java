/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public interface Builder<T extends Builder<T, Q>, Q> {

    default T self() {
        return (T) this;
    }

    Q build();

}
