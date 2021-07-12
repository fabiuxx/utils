/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.text;

import fa.gs.utils.adapters.Adapter;
import fa.gs.utils.collections.Lists;
import java.util.Iterator;
import java8.util.function.Function;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Joiner<T> {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Objetos a unir.
     */
    private Iterable<T> objects;

    /**
     * Prefijo para cada objeto.
     */
    private String prefix;

    /**
     * Posfijo para cada objeto.
     */
    private String posfix;

    /**
     * Separador entre objetos.
     */
    private String separator;

    /**
     * Adaptador de objetos.
     */
    private Adapter<T, String> adapter;
    //</editor-fold>

    /**
     * Constructor.
     */
    private Joiner() {
        this.prefix = "";
        this.posfix = "";
        this.separator = ",";
        this.adapter = null;
    }

    /**
     * Inicializador estatico.
     *
     * @param <T> Parametro de tipo de los objetos a unir.
     * @param objects Cantidad arbitraria de objetos a unir.
     * @return Instancia de esta clase.
     */
    public static <T> Joiner<T> of(T... objects) {
        Iterable<T> iterables = Lists.wrap(objects);
        return Joiner.of(iterables);
    }

    /**
     * Inicializador estatico.
     *
     * @param objects Cantidad arbitraria de objetos a unir.
     * @return Instancia de esta clase.
     */
    public static <T> Joiner<T> of(Iterable<T> objects) {
        Joiner joiner = new Joiner();
        joiner.objects = objects;
        return joiner;
    }

    /**
     * Establece el prefijo a utilizar.
     *
     * @param prefix Prefijo a utilizar.
     * @return Misma instancia de esta clase.
     */
    public Joiner<T> prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Establece el posfijo a utilizar.
     *
     * @param posfix Posfijo a utilizar.
     * @return Misma instancia de esta clase.
     */
    public Joiner<T> posfix(String posfix) {
        this.posfix = posfix;
        return this;
    }

    /**
     * Establece el separador a utilizar.
     *
     * @param separator Separador entre elementos.
     * @return Misma instancia de esta clase.
     */
    public Joiner<T> separator(String separator) {
        this.separator = separator;
        return this;
    }

    /**
     * Establece al caracter {@code "'"} como prefijo y posfijo para elementos.
     *
     * @return Misma instancia de esta clase.
     */
    public Joiner<T> quoted() {
        return this.prefix("'").posfix("'");
    }

    /**
     * Establece el adaptador que transforma objetos arbitrarios a cadenas de
     * texto que pueden ser unificadas.
     *
     * @param adapter Adaptador.
     * @return Misma instancia de esta clase.
     */
    public Joiner<T> adapter(Adapter<T, String> adapter) {
        this.adapter = adapter;
        return this;
    }

    /**
     * Establece la funcion que transforma objetos arbitrarios a cadenas de
     * texto que pueden ser unificadas.
     *
     * @param mapper Funcion de mapeo.
     * @return Misma instancia de esta clase.
     */
    public Joiner<T> adapter(Function<T, String> mapper) {
        return adapter((T obj, Object... args) -> mapper.apply(obj));
    }

    /**
     * Une los objetos indicados.
     *
     * @return Cadena que contiene cada representacion en cadena individual de
     * los objetoso indicados.
     */
    public String join() {
        StringBuilder builder = new StringBuilder();
        Iterator<T> it = objects.iterator();
        while (it.hasNext()) {
            T object = it.next();
            if (object != null) {
                builder.append(prefix);
                if (adapter != null) {
                    builder.append(adapter.adapt(object));
                } else {
                    builder.append(String.valueOf(object));
                }
                builder.append(posfix);
            }
            if (it.hasNext()) {
                builder.append(separator);
            }
        }
        return builder.toString();
    }

}
