/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.adapters.impl;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class Adapter0<TFrom, TTo> extends Adapter1<TFrom, TTo, Void> {

    @Override
    public TTo adapt(TFrom obj, Void p1) {
        return adapt(obj);
    }

    public abstract TTo adapt(TFrom obj);

}
