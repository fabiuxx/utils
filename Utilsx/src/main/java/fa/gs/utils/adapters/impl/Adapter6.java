/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package fa.gs.utils.adapters.impl;

import fa.gs.utils.adapters.Adapter;
import fa.gs.utils.misc.Args;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class Adapter6<TFrom, TTo, P1, P2, P3, P4, P5, P6> implements Adapter<TFrom, TTo> {

    @Override
    public final TTo adapt(TFrom obj, Object... args) {
        P1 p1 = Args.argv(args, 0, null);
        P2 p2 = Args.argv(args, 1, null);
        P3 p3 = Args.argv(args, 2, null);
        P4 p4 = Args.argv(args, 3, null);
        P5 p5 = Args.argv(args, 4, null);
        P6 p6 = Args.argv(args, 5, null);
        return adapt(obj, p1, p2, p3, p4, p5, p6);
    }

    public abstract TTo adapt(TFrom obj, P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);

}
