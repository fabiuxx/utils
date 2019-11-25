/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters;

import fa.gs.utils.adapters.construction.Constructor;
import fa.gs.utils.adapters.construction.NormalConstructionStrategy;
import fa.gs.utils.adapters.construction.UnsafeConstructionStrategy;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Assertions;
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
    private static volatile Cache CACHE = null;

    /**
     * Constructor que implementa una estrategia normal de instanciacion.
     */
    private static final Constructor normalConstructor;

    /**
     * Constructor que implementa una estrategia no administrada (segura) de
     * instanciacion.
     */
    private static final Constructor unsafeConstructor;
    //</editor-fold>

    /**
     * Inicializador estatico.
     */
    static {
        // Inicializar cache.
        Cache CACHE_ = Adapters.CACHE;
        if (CACHE_ == null) {
            synchronized (Adapters.class) {
                CACHE_ = Adapters.CACHE;
                if (CACHE_ == null) {
                    CACHE_ = Cache.instance(200);
                    Adapters.CACHE = CACHE_;
                }
            }
        }

        // Inicializar constructores.
        normalConstructor = new NormalConstructionStrategy();
        unsafeConstructor = new UnsafeConstructionStrategy();
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
     * @param <TFrom>
     * @param <TTo>
     * @param adapterClass
     * @param objs
     * @param args
     * @return
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

        // Determinar estrategia de creacion.
        Construction.Strategy strategy = Construction.Strategy.NORMAL;
        if (adapterClass.isAnnotationPresent(Construction.class)) {
            Construction c = (Construction) adapterClass.getAnnotation(Construction.class);
            strategy = c.strategy();
        }

        // Obtener una instancia del adaptador desde cache o instanciar uno nuevo.
        Adapter adapter = CACHE.get(adapterClass);
        if (adapter == null) {
            try {
                adapter = create(adapterClass, strategy);
                CACHE.put(adapterClass, adapter);
            } catch (Throwable thr) {
                thr.printStackTrace(System.err);
                adapter = null;
            }
        }
        return adapter;
    }

    /**
     * Instancia un nuevo adaptador dependiendo de su estrategia de
     * construccion.
     *
     * @param klass Clase del adaptador.
     * @param strategy Estrategia de construccion.
     * @return Nueva instancia, si hubiere.
     * @throws Throwable Error producido durante la construccion.
     */
    private static Adapter create(Class<? extends Adapter> klass, Construction.Strategy strategy) throws Throwable {
        switch (strategy) {
            case NORMAL:
                return normalConstructor.instantiate(klass);
            case UNSAFE:
                return unsafeConstructor.instantiate(klass);
            default:
                return null;
        }
    }

}
