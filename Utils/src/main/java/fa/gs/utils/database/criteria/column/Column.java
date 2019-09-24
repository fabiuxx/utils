/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria.column;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public interface Column<T> extends Serializable {

    Column<?> getParent();

    String getName();

    Class<T> getType();

    boolean accepsNull();

    /**
     * Comprueba si la columna acepta el valor indicado.
     *
     * @param value Valor.
     * @return {@code true} si la columna acepta el valor como asignacion, caso
     * contrario {@code false}.
     */
    public boolean accepts(Object value);

    /**
     * Comprueba si la columna acepta el array de valores indicado.
     *
     * @param values Array de valores.
     * @return {@code true} si la columna acepta el valor como asignacion, caso
     * contrario {@code false}.
     */
    public boolean accepts(Object[] values);

}
