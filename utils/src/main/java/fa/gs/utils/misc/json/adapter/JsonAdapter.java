/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.adapter;

import fa.gs.utils.adapters.Adapter;

/**
 *
 * @author Fabio A. González Sosa
 * @param <TFrom> Parametro de tipo de entrada.
 * @param <TTo> Parametro de tipo de salida.
 */
public interface JsonAdapter<TFrom, TTo> extends Adapter<TFrom, TTo> {

    Class<TFrom> getInputConversionType();

    Class<TTo> getOutputConversionType();

}
