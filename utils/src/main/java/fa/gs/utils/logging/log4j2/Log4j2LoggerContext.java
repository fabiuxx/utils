/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.log4j2;

import fa.gs.utils.logging.LoggerContext;
import fa.gs.utils.misc.Ids;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.ThreadContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Log4j2LoggerContext implements LoggerContext {

    /**
     * Constructor.
     */
    private Log4j2LoggerContext() {
        ;
    }

    /**
     * Inicializador estatico.
     *
     * @return Instancia de esta clase.
     */
    public static LoggerContext instance() {
        return new Log4j2LoggerContext();
    }

    /**
     * Agrega un nuevo identificador de seguimiento de contexto. Idealmente debe
     * existir solo un identificador en la pila.
     */
    @Override
    public void push() {
        push(Ids.randomUuid());
    }

    /**
     * Agrega un nuevo identificador de seguimiento de contexto. Idealmente debe
     * existir solo un identificador en la pila.
     *
     * @param tag Identificador de seguimiento.
     */
    @Override
    public void push(String tag) {
        ThreadContext.push(tag);
    }

    /**
     * Agrega un nuevo parametro de seguimiento de contexto.
     *
     * @param key Identificador de parametro de seguimiento.
     * @param value Valor de parametro.
     */
    @Override
    public void push(String key, Object value) {
        ThreadContext.put(key, String.valueOf(value));
    }

    /**
     * Permite obtener el identificador actual de seguimiento de contexto, si
     * hubiere, sin eliminar el mismo desde la pila de identificadores.
     *
     * @return Identificador de seguimiento de contexto.
     */
    @Override
    public String peek() {
        return ThreadContext.peek();
    }

    /**
     * Permite obtener el valor actual del parametro de seguimiento de contexto,
     * si hubiere, sin eliminar el mismo.
     *
     * @param key Identificador de seguimiento de contexto.
     * @return Valor de parametro de seguimiento, si hubiere.
     */
    @Override
    public String peek(String key) {
        return ThreadContext.get(key);
    }

    /**
     * Permite obtener el valor actual de los parametros de seguimiento de
     * contexto.
     *
     * @return Valores de parametros de seguimiento.
     */
    @Override
    public Map<String, Object> peekAll() {
        Map<String, Object> values = new HashMap<>();
        values.putAll(ThreadContext.getContext());
        return values;
    }

    /**
     * Elimina todos los identificadores de seguimiento de contexto agregados.
     * Idealmente debe existir solo un identificador en la pila.
     */
    @Override
    public void pop() {
        ThreadContext.pop();
        if (ThreadContext.getDepth() <= 0) {
            ThreadContext.clearStack();
        }
    }

    /**
     * Elimina todos los valores de parametro de seguimiento de contexto
     * agregados.
     *
     * @param key Identificador de seguimiento de contexto.
     */
    @Override
    public void pop(String key) {
        ThreadContext.remove(key);
    }

    /**
     * Elimina todos los parametros de seguimiento de contexto.
     */
    @Override
    public void clear() {
        ThreadContext.pop();
        ThreadContext.clearMap();
    }

}
