/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.adapters;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Cache {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Almacenamiento de elementos cacheados.
     */
    private LRUCache<Class<? extends Adapter>, Adapter> storage;

    /**
     * Lock para operaciones.
     */
    private final ReentrantReadWriteLock lock;
    //</editor-fold>

    /**
     * Constructor.
     *
     * @param capacity Capacidad de almacenamiento.
     */
    private Cache(int capacity) {
        this.storage = new LRUCache<>(capacity);
        this.lock = new ReentrantReadWriteLock(true);
    }

    /**
     * Inicializador estatico.
     *
     * @param capacity Capacidad de almacenamiento.
     * @return Instancia de esta clase.
     */
    public static Cache instance(int capacity) {
        return new Cache(capacity);
    }

    /**
     * Agrega un elemento a la cache.
     *
     * @param key Clave.
     * @param value Valor.
     */
    public void put(Class<? extends Adapter> key, Adapter value) {
        lock.writeLock().lock();
        try {
            storage.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Obtiene el valor almacenado en cache asociado a una clave dada, si
     * hubiere.
     *
     * @param key Clave.
     * @return Valor almacenado, si hubiere.
     */
    public Adapter get(Class<? extends Adapter> key) {
        lock.readLock().lock();
        try {
            Object obj = storage.get(key);
            return (obj != null) ? (Adapter) obj : null;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Modifica la capacidad de la cache.
     *
     * @param capacity Nueva capacidad de la cache.
     */
    public void resize(int capacity) {
        lock.writeLock().lock();
        try {
            LRUCache<Class<? extends Adapter>, Adapter> tmp = storage;
            this.storage = new LRUCache<>(capacity);
            this.storage.putAll(tmp);
            tmp.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Clase que implementa una cache con una politica LRU (Least-Recently-Used)
     * para el reemplazo de elementos.
     */
    private static class LRUCache<K, V> extends LinkedHashMap<K, V> {

        /**
         * Tamaño de la cache.
         */
        private final int capacity;

        /**
         * Constructor.
         *
         * @param cacheSize Tamaño de la cache.
         */
        public LRUCache(int cacheSize) {
            super(15, 0.75f, true);
            this.capacity = cacheSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() >= capacity;
        }

    }

}
