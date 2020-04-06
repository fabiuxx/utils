/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

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

    public static <T> int size(byte[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static <T> int size(T[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

}
