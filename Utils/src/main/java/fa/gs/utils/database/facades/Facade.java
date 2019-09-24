/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.facades;

import fa.gs.utils.database.criteria.QueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public interface Facade<T> extends Serializable {

    /**
     * Elimina una entidad dada del contexto de persistencia, indicando que
     * cualquier modificacion sobre la misma no tendra impacto final dentro de
     * la base de datos.
     *
     * @param entity Entidad.
     */
    public void detach(T entity);

    /**
     * Obtiene un entero que representa la cantidad total de registros en una
     * tabla.
     *
     * @return Cantidad de registros en una tabla.
     */
    public Integer count();

    /**
     * Obtiene un entero que representa la cantidad total de registros en una
     * tabla utilizando criterios avanzadas de busqueda.
     *
     * @param criteria Criterios avanzados de busqueda que permite la inclusion
     * de filtros y predicados de ordenacion de atributos.
     * @return Cantidad de registros en una tabla.
     */
    public Integer count(QueryCriteria criteria);

    /**
     * Crea un nuevo registro para la entidad.
     *
     * @param entity Objeto a ser persistido.
     * @return Entidad persistida.
     */
    public T create(T entity);

    /**
     * Guarda los cambios efectuados sobre una entidad.
     *
     * @param entity Objeto cuyas modificaciones seran persistidas.
     * @return Entidad modificada.
     */
    public T edit(T entity);

    /**
     * Elimina el registro asociado a la entidad.
     *
     * @param entity Objeto a ser eliminado.
     */
    public void remove(T entity);

    /**
     * Refresca el estado interno de la entidad.
     *
     * @param entity Objeto a ser refrescado.
     * @return Entidad actualizada.
     */
    public T refresh(T entity);

    /**
     * Agrega una entidad no administrada al contexto de persistencia.
     *
     * @param entity Objeto a ser agregado.
     * @return Entidad incluida en contexto de persistencia.
     */
    public T merge(T entity);

    /**
     * Obtiene la información de una entidad respecto a un Identificador.
     *
     * @param id Identificador de la entidad solicitada.
     * @return Objeto asociado al Identificador.
     */
    public T find(Object id);

    /**
     * Obtiene la lista completa de registros en una tabla.
     *
     * @return Lista de registros.
     */
    public List<T> findAll();

    /**
     * Obtiene la informacion de una entidad utilizando criterios avanzadas de
     * busqueda.
     *
     * @param criteria Criterios avanzados de busqueda que permite la inclusion
     * de filtros y predicados de ordenacion de atributos.
     * @return Lista de objetos que coinciden con los criterios de busqueda.
     */
    public List<T> find(QueryCriteria criteria);

    /**
     * Obtiene la informacion de una entidad que coincida con criterios
     * avanzados de busqueda.
     *
     * @param criteria Criterios avanzados de busqueda que permite la inclusion
     * de filtros y predicados de ordenacion de atributos.
     * @return Objeto que coincide con los criterios de busqueda.
     */
    public T findFirst(QueryCriteria criteria);

}
