/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.adapters.impl;

import py.com.generica.utils.adapters.Adapter;
import py.com.generica.utils.misc.Args;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class Adapter2<TFrom, TTo, P1, P2> implements Adapter<TFrom, TTo> {

    @Override
    public final TTo adapt(TFrom obj, Object... args) {
        P1 p1 = Args.argv(args, 0, null);
        P2 p2 = Args.argv(args, 1, null);
        return adapt(obj, p1, p2);
    }

    public abstract TTo adapt(TFrom obj, P1 p1, P2 p2);

}
