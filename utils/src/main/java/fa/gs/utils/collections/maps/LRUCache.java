/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections.maps;

import fa.gs.utils.adapters.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Fabio A. González Sosa
 */
public class LRUCache<K, V> {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Almacenamiento de elementos cacheados.
     */
    private LRUMap<K, V> storage;

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
    private LRUCache(int capacity) {
        this.storage = new LRUMap<>(capacity);
        this.lock = new ReentrantReadWriteLock(true);
    }

    /**
     * Inicializador estatico.
     *
     * @param capacity Capacidad de almacenamiento.
     * @return Instancia de esta clase.
     */
    public static LRUCache instance(int capacity) {
        return new LRUCache(capacity);
    }

    /**
     * Agrega un elemento a la cache.
     *
     * @param key Clave.
     * @param value Valor.
     */
    public void put(K key, V value) {
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
    public Adapter get(K key) {
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
            LRUMap<K, V> tmp = storage;
            this.storage = new LRUMap<>(capacity);
            this.storage.putAll(tmp);
            tmp.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

}
