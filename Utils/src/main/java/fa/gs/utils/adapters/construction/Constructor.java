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
public interface Constructor {

    /**
     * Instancia un nuevo adaptador.
     *
     * @param adapterClass Clase del adaptador.
     * @return Nueva instancia, si hubiere.
     * @throws Throwable Error producido durante la construccion.
     */
    public Adapter instantiate(Class<? extends Adapter> adapterClass) throws Throwable;

}
