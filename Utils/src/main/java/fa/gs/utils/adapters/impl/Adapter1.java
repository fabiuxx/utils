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
public abstract class Adapter1<TFrom, TTo, P1> implements Adapter<TFrom, TTo> {

    @Override
    public final TTo adapt(TFrom obj, Object... args) {
        P1 p1 = Args.argv(args, 0, null);
        return adapt(obj, p1);
    }

    public abstract TTo adapt(TFrom obj, P1 p1);

}
