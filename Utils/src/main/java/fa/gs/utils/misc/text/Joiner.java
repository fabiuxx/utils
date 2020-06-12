/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.text;

import fa.gs.utils.adapters.Adapter;
import fa.gs.utils.collections.Lists;
import java.util.Iterator;
import java.util.function.Function;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Joiner {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Objetos a unir.
     */
    private Iterable<Object> objects;

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
    private Adapter<Object, String> adapter;
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
    public static <T> Joiner of(T... objects) {
        Iterable<T> iterables = Lists.wrap(objects);
        return Joiner.of(iterables);
    }

    /**
     * Inicializador estatico.
     *
     * @param objects Cantidad arbitraria de objetos a unir.
     * @return Instancia de esta clase.
     */
    public static Joiner of(Iterable objects) {
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
    public Joiner prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Establece el posfijo a utilizar.
     *
     * @param posfix Posfijo a utilizar.
     * @return Misma instancia de esta clase.
     */
    public Joiner posfix(String posfix) {
        this.posfix = posfix;
        return this;
    }

    /**
     * Establece el separador a utilizar.
     *
     * @param separator Separador entre elementos.
     * @return Misma instancia de esta clase.
     */
    public Joiner separator(String separator) {
        this.separator = separator;
        return this;
    }

    /**
     * Establece al caracter {@code "'"} como prefijo y posfijo para elementos.
     *
     * @return Misma instancia de esta clase.
     */
    public Joiner quoted() {
        return this.prefix("'").posfix("'");
    }

    /**
     * Establece el adaptador que transforma objetos arbitrarios a cadenas de
     * texto que pueden ser unificadas.
     *
     * @param adapter Adaptador.
     * @return Misma instancia de esta clase.
     */
    public Joiner adapter(Adapter<Object, String> adapter) {
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
    public Joiner adapter(Function<Object, String> mapper) {
        Adapter<Object, String> adapter = new Adapter<Object, String>() {
            @Override
            public String adapt(Object obj, Object... args) {
                return mapper.apply(obj);
            }
        };
        return adapter(adapter);
    }

    /**
     * Une los objetos indicados.
     *
     * @return Cadena que contiene cada representacion en cadena individual de
     * los objetoso indicados.
     */
    public String join() {
        StringBuilder builder = new StringBuilder();
        Iterator it = objects.iterator();
        while (it.hasNext()) {
            Object object = it.next();
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
