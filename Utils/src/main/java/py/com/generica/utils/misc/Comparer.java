/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 * @param <A>
 * @param <B>
 */
public interface Comparer<A, B> {

    public boolean equals(A a, B b);

}
