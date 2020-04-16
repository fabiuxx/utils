/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections.maps;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 * @param <K> Parametro de tipo de clave.
 * @param <V> Parametro de tipo de valor.
 */
public class LRUMap<K, V> extends LinkedHashMap<K, V> {

    /**
     * Tamaño de la cache.
     */
    private final int capacity;

    /**
     * Constructor.
     *
     * @param cacheSize Tamaño de la cache.
     */
    public LRUMap(int cacheSize) {
        super(15, 0.75f, true);
        this.capacity = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= capacity;
    }

}
