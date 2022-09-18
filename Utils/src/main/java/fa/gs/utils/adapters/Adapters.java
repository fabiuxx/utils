/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.maps.LRUCache;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Reflection;
import java.lang.reflect.Modifier;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Adapters {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Cache para adaptadores mas utilizados.
     */
    private static volatile LRUCache<Class<? extends Adapter>, Adapter> CACHE = null;
    //</editor-fold>

    /**
     * Inicializador estatico.
     */
    static {
        // Inicializar cache.
        LRUCache<Class<? extends Adapter>, Adapter> CACHE_ = Adapters.CACHE;
        if (CACHE_ == null) {
            synchronized (Adapters.class) {
                CACHE_ = Adapters.CACHE;
                if (CACHE_ == null) {
                    CACHE_ = LRUCache.instance(200);
                    Adapters.CACHE = CACHE_;
                }
            }
        }
    }

    /**
     * Permite adaptar un objeto indicando la clase del adaptador a utilizar y
     * los posibles argumentos opcionales necesarios.
     *
     * @param <TFrom> Parametro de tipo de origen.
     * @param <TTo> Parametro de tipo de destino.
     * @param adapterClass Clase del adaptador a utilizar.
     * @param obj Objeto a adaptar.
     * @param args Argumentos opcionales para el adaptador.
     * @return Objeto adaptado, si la operacion tiene exito. Caso contrario
     * {@code null}.
     */
    public static <TFrom, TTo> TTo adapt(Class<? extends Adapter<TFrom, TTo>> adapterClass, TFrom obj, Object... args) {
        Adapter adapter = instantiate(adapterClass);
        Object adapted = adapter.adapt(obj, args);
        return (adapted != null) ? (TTo) adapted : null;
    }

    /**
     * Permite adaptar una coleccion de objetos a
     *
     * @param <TFrom> Parametro de tipo.
     * @param <TTo> Parametro de tipo.
     * @param adapterClass Clase del adaptador a utilizar.
     * @param objs Objetos a adaptar.
     * @param args Argumentos opcionales para el adaptador.
     * @return Objetos adaptados, si la operacion tiene exito. Caso contrario {@code null}.
     */
    public static <TFrom, TTo> Collection<TTo> adapt(Class<? extends Adapter<TFrom, TTo>> adapterClass, Collection<TFrom> objs, Object... args) {
        Collection<TTo> list = Lists.empty();
        if (!Assertions.isNullOrEmpty(objs)) {
            for (TFrom obj : objs) {
                TTo obj0 = adapt(adapterClass, obj, args);
                list.add(obj0);
            }
        }
        return list;
    }

    /**
     * Instancia un nuevo adaptador.
     *
     * @param adapterClass Clase del adaptador.
     * @return Instancia concreta de un adaptador, si la operacion tiene exito.
     * Caso contrario {@code null}.
     */
    public static Adapter instantiate(Class<? extends Adapter> adapterClass) {
        // Omitir clases abstractas.
        if (Modifier.isAbstract(adapterClass.getModifiers())) {
            return null;
        }

        // Obtener una instancia del adaptador desde cache o instanciar uno nuevo.
        Adapter adapter = CACHE.get(adapterClass);
        if (adapter == null) {
            try {
                adapter = Reflection.tryCreateInstance(adapterClass);
                CACHE.put(adapterClass, adapter);
            } catch (Throwable thr) {
                thr.printStackTrace(System.err);
                adapter = null;
            }
        }
        return adapter;
    }

}
