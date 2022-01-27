/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections.maps;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.collections.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Fabio A. González Sosa
 */
public class CollectionGroupMap<K, V> {

    private final Map<K, Set<V>> map;

    public CollectionGroupMap() {
        this.map = Maps.empty();
    }

    public final void initialize(K[] keys) {
        Maps.initialize(map, keys);
    }

    public final void initialzie(K[] keys) {
        Maps.initialize(map, keys, Sets.empty());
    }

    public void put(K key, V value) {
        if (key == null) {
            return;
        }

        Set<V> set = Maps.get(map, key, Sets.<V>empty());
        if (mustClearBeforePut(set)) {
            set.clear();
        }

        set.add(value);
        map.put(key, set);
    }

    public void put(K key, Collection<V> values) {
        Set<V> set = Maps.get(map, key, Sets.<V>empty());
        set.addAll(values);
        map.put(key, set);
    }

    private boolean mustClearBeforePut(Set<V> set) {
        /**
         * Al inicializar cada "slot" del grupo, se utiliza el valor null para
         * rellenar el slot. Al agregar un nuevo valor, se debe omitir el valor
         * "nulo" de relleno para evitar inconsistencias.
         */
        if (set.size() == 1) {
            V value = set.iterator().next();
            if (value == null) {
                return true;
            }
        }
        return false;
    }

    public Map<K, Collection<V>> getMap() {
        /**
         * Se transforma la coleccion de itemes desde un 'set' a una 'lista' ya
         * que la primera estructura de datos no es soportada totalmente por
         * tags JSF. Fuente: https://stackoverflow.com/a/4017852/1284724
         */
        Map<K, Collection<V>> map0 = Maps.empty();

        for (Map.Entry<K, Set<V>> entry : map.entrySet()) {
            K key = entry.getKey();
            Set<V> set = entry.getValue();
            map0.put(key, Lists.wrap(set));
        }

        return Collections.unmodifiableMap(map0);
    }

}
