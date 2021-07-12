/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections;

import fa.gs.utils.collections.maps.CollectionGroupMap;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.Codificables;
import fa.gs.utils.misc.Reflection;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Maps {

    public static <K, V> Map<K, V> empty() {
        return new LinkedHashMap<>();
    }

    public static <K, V> void initialize(Map<K, V> map, K[] keys) {
        initialize(map, keys, null);
    }

    public static <K, V> void initialize(Map<K, V> map, K[] keys, V defaultValue) {
        if (!Assertions.isNullOrEmpty(map) && !Assertions.isNullOrEmpty(keys)) {
            for (K key : keys) {
                map.put(key, defaultValue);
            }
        }
    }

    public static <K, V> Map<K, V> map(Collection<V> values, String attribute, Class<K> type) {
        Map<K, V> map = empty();
        for (V value : values) {
            K key = Reflection.get(value, attribute, type);
            if (key != null) {
                map.put(key, value);
            }
        }
        return map;
    }

    public static <K, V> CollectionGroupMap<K, V> groupBy(Collection<V> values, String attribute, Class<K> type) {
        CollectionGroupMap<K, V> groups = new CollectionGroupMap<>();
        for (V value : values) {
            K key = Reflection.get(value, attribute, type);
            groups.put(key, value);
        }
        return groups;
    }

    public static <K, V> V get(Map<K, V> map, K key) {
        return Maps.get(map, key, null);
    }

    public static <K, V> V get(Map<K, V> map, K key, V fallback) {
        // Control de seguridad.
        if (map == null) {
            return fallback;
        }

        if (map.containsKey(key)) {
            V value = map.get(key);
            if (value == null) {
                return fallback;
            } else {
                return value;
            }
        } else {
            return fallback;
        }
    }

    public static <K, V, T> T get(Map<K, V> map, K key, T fallback, Class<T> klass) {
        V value = Maps.get(map, key);
        if (value != null && klass.isInstance(value)) {
            return klass.cast(value);
        } else {
            return fallback;
        }
    }

    public static <K, V> Boolean bool(Map<K, V> map, K key) {
        return bool(map, key, null);
    }

    public static <K, V> Boolean bool(Map<K, V> map, K key, Boolean fallback) {
        return get(map, key, fallback, Boolean.class);
    }

    public static <K, V> String string(Map<K, V> map, K key) {
        return string(map, key, null);
    }

    public static <K, V> String string(Map<K, V> map, K key, String fallback) {
        return get(map, key, fallback, String.class);
    }

    public static <K, V> Character character(Map<K, V> map, K key) {
        return character(map, key, null);
    }

    public static <K, V> Character character(Map<K, V> map, K key, Character fallback) {
        return get(map, key, fallback, Character.class);
    }

    public static <K, V> Integer integer(Map<K, V> map, K key) {
        return integer(map, key, null);
    }

    public static <K, V> Integer integer(Map<K, V> map, K key, Integer fallback) {
        return get(map, key, fallback, Integer.class);
    }

    public static <K, V> Long long0(Map<K, V> map, K key) {
        return long0(map, key, null);
    }

    public static <K, V> Long long0(Map<K, V> map, K key, Long fallback) {
        return get(map, key, fallback, Long.class);
    }

    public static <K, V> BigInteger biginteger(Map<K, V> map, K key) {
        return biginteger(map, key, null);
    }

    public static <K, V> BigInteger biginteger(Map<K, V> map, K key, BigInteger fallback) {
        return get(map, key, fallback, BigInteger.class);
    }

    public static <K, V> BigDecimal bigdecimal(Map<K, V> map, K key) {
        return bigdecimal(map, key, null);
    }

    public static <K, V> BigDecimal bigdecimal(Map<K, V> map, K key, BigDecimal fallback) {
        return get(map, key, fallback, BigDecimal.class);
    }

    public static <K, V> Date date(Map<K, V> map, K key) {
        return date(map, key, null);
    }

    public static <K, V> Date date(Map<K, V> map, K key, Date fallback) {
        return get(map, key, fallback, Date.class);
    }

    public static <K, V, T extends Enum<T> & Codificable> T codificable(Map<K, V> map, K key, Class<T> klass) {
        String value = string(map, key);
        return Codificables.fromCodigo(value, klass);
    }

    public static <K, V, T extends Codificable> T codificable(Map<K, V> map, K key, T[] values) {
        String value = string(map, key);
        return Codificables.fromCodigo(value, values);
    }

}
