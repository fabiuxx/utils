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
public abstract class Adapter7<TFrom, TTo, P1, P2, P3, P4, P5, P6, P7> implements Adapter<TFrom, TTo> {

    @Override
    public final TTo adapt(TFrom obj, Object... args) {
        P1 p1 = Args.argv(args, 0, null);
        P2 p2 = Args.argv(args, 1, null);
        P3 p3 = Args.argv(args, 2, null);
        P4 p4 = Args.argv(args, 3, null);
        P5 p5 = Args.argv(args, 4, null);
        P6 p6 = Args.argv(args, 5, null);
        P7 p7 = Args.argv(args, 6, null);
        return adapt(obj, p1, p2, p3, p4, p5, p6, p7);
    }

    public abstract TTo adapt(TFrom obj, P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7);

}
