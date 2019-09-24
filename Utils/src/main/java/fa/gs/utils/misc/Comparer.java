/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 * @param <A> Parametro de tipo 1.
 * @param <B> Parametro de tipo 2.
 */
public interface Comparer<A, B> {

    public boolean equals(A a, B b);

}
