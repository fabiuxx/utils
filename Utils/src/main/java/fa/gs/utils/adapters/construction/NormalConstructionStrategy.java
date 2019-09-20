/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters.construction;

import fa.gs.utils.adapters.Adapter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class NormalConstructionStrategy implements Constructor {

    @Override
    public Adapter instantiate(Class<? extends Adapter> klass) throws Throwable {
        return klass.newInstance();
    }

}
