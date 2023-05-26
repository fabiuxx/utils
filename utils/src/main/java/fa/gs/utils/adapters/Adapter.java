/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters;

/**
 *
 * @author Fabio A. González Sosa
 * @param <TFrom> Tipo de Origen.
 * @param <TTo> Tipo de Destino.
 */
@FunctionalInterface
public interface Adapter<TFrom, TTo> {

    /**
     * Permite adaptar un objeto de tipo {@code TFrom} a otro de tipo
     * {@code TTo}.
     *
     * @param obj Instancia a adaptar.
     * @param args Parametros opcionales necesarios durante la adaptacion.
     * @return Instancia de objeto adaptado.
     */
    TTo adapt(TFrom obj, Object... args);
}
