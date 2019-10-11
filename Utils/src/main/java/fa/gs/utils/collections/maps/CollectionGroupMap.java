/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections.maps;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class CollectionGroupMap<K, V> {

    private final Map<K, Collection<V>> map;

    public CollectionGroupMap() {
        this.map = Maps.empty();
    }

    public void put(K key, V value) {
        if (key == null) {
            return;
        }

        Collection<V> collection = Maps.get(map, key, Lists.empty());
        collection.add(value);
        map.put(key, collection);
    }

    public void put(K key, Collection<V> values) {
        Collection<V> collection = Maps.get(map, key, Lists.empty());
        collection.addAll(values);
        map.put(key, collection);
    }

    public Map<K, Collection<V>> getMap() {
        return Collections.unmodifiableMap(map);
    }

}
