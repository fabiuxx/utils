/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.facade;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public interface EntityFacade<T> extends Serializable {

    /**
     * Obtiene el tipo concreto de la entidad.
     *
     * @return Tipo de la entidad.
     */
    Class<T> getEntityClass();

    /**
     * Agrega una entidad dada al contexto de persistencia, a manera que las
     * modificaciones sobre el mismo tendran impacto en la base de datos.
     *
     * @param entity Entidad.
     */
    void attach(T entity);

    /**
     * Elimina una entidad dada del contexto de persistencia, indicando que
     * cualquier modificacion sobre la misma no tendra impacto final dentro de
     * la base de datos.
     *
     * @param entity Entidad.
     */
    void detach(T entity);

    /**
     * Crea un nuevo registro para la entidad.
     *
     * @param entity Objeto a ser persistido.
     * @return Entidad persistida.
     */
    T create(T entity);

    /**
     * Guarda los cambios efectuados sobre una entidad.
     *
     * @param entity Objeto cuyas modificaciones seran persistidas.
     * @return Entidad modificada.
     */
    T edit(T entity);

    /**
     * Elimina el registro asociado a la entidad.
     *
     * @param entity Objeto a ser eliminado.
     */
    void remove(T entity);

}
