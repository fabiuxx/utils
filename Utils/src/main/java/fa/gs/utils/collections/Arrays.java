/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections;

import fa.gs.utils.database.criteria.column.Column;
import fa.gs.utils.misc.Reflect;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Arrays {

    public static <T> T[] create(Class<T> type, int size) {
        T[] array = (T[]) Array.newInstance(type, size);
        return array;
    }

    public static <T> T[] array(Collection<T> collection, Class<T> type) {
        Enumeration<T> enumeration = Collections.enumeration(collection);
        ArrayList<T> list = Collections.list(enumeration);
        T[] array = (T[]) Array.newInstance(type, list.size());
        return list.toArray(array);
    }

    public static <T, S> S[] array(Collection<T> collection, Column<S> column) {
        return array(collection, column.getName(), column.getType());
    }

    public static <T, S> S[] array(Collection<T> collection, String attribute, Class<S> type) {
        Set<S> set = new HashSet<>();
        for (T element : collection) {
            S value = Reflect.get(element, attribute, type);
            if (value != null) {
                set.add(value);
            }
        }

        S[] array = create(type, set.size());
        return set.toArray(array);
    }

    public static <T> List<T> list(T... elements) {
        List<T> list = Lists.empty();
        if (elements != null) {
            for (T element : elements) {
                list.add(element);
            }
        }
        return list;
    }

}
