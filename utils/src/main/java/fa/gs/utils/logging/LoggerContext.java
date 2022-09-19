/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging;

import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface LoggerContext {

    /**
     * Agrega un nuevo identificador de seguimiento de contexto. Idealmente debe
     * existir solo un identificador en la pila.
     */
    public void push();

    /**
     * Agrega un nuevo identificador de seguimiento de contexto. Idealmente debe
     * existir solo un identificador en la pila.
     *
     * @param tag Identificador de seguimiento.
     */
    public void push(String tag);

    /**
     * Agrega un nuevo parametro de seguimiento de contexto.
     *
     * @param key Identificador de parametro de seguimiento.
     * @param value Valor de parametro.
     */
    public void push(String key, Object value);

    /**
     * Permite obtener el identificador actual de seguimiento de contexto, si
     * hubiere, sin eliminar el mismo desde la pila de identificadores.
     *
     * @return Identificador de seguimiento de contexto.
     */
    public String peek();

    /**
     * Permite obtener el valor actual del parametro de seguimiento de contexto,
     * si hubiere, sin eliminar el mismo.
     *
     * @param key Identificador de seguimiento de contexto.
     * @return Valor de parametro de seguimiento, si hubiere.
     */
    public Object peek(String key);

    /**
     * Permite obtener el valor actual de los parametros de seguimiento de
     * contexto.
     *
     * @return Valores de parametros de seguimiento.
     */
    public Map<String, Object> peekAll();

    /**
     * Elimina todos los identificadores de seguimiento de contexto agregados.
     * Idealmente debe existir solo un identificador en la pila.
     */
    public void pop();

    /**
     * Elimina todos los valores de parametro de seguimiento de contexto
     * agregados.
     *
     * @param key Identificador de seguimiento de contexto.
     */
    public void pop(String key);

    /**
     * Elimina todos los parametros de seguimiento de contexto.
     */
    public void clear();

}
