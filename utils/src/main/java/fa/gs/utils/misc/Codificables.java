/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.misc.errors.Errors;
import java.util.Objects;
import java.util.TreeMap;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Codificables {

    public static <T extends Codificable> T fromCodigo(String codigo, T[] values) {
        if (!Assertions.isNullOrEmpty(values)) {
            for (T value : values) {
                if (Objects.equals(codigo, value.codigo())) {
                    return value;
                }
            }
        }
        throw Errors.illegalArgument();
    }

    public static <T extends Codificable> T fromCodigo(String codigo, Class<T> klass) {
        if (klass.isEnum()) {
            return fromCodigo(codigo, klass.getEnumConstants());
        }
        throw Errors.illegalArgument();
    }

    public static void listarCoincidencias(Codificable[] values) {
        TreeMap<String, Integer> map = new TreeMap<>();
        for (Codificable codificable : values) {
            String key = codificable.codigo();
            if (!map.containsKey(key)) {
                map.put(key, 0);
            }
            map.put(key, map.get(key) + 1);
        }
        for (String key : map.keySet()) {
            Integer count = map.get(key);
            System.out.printf("%s => %s\n", key, count);
        }
    }

    /**
     * Verifica que cada codigo definido sea unico y retorna alguno que tenga
     * mas de una correspondencia.
     *
     * @param values Valores codificados.
     * @return codigo repetido, si hubiere. Caso contrario {@code null}.
     */
    public static String obtenerRepetido(Codificable[] values) {
        TreeMap<String, Integer> map = new TreeMap<>();
        for (Codificable codificable : values) {
            String key = codificable.codigo();
            if (!map.containsKey(key)) {
                map.put(key, 0);
            }
            map.put(key, map.get(key) + 1);
        }
        for (String key : map.keySet()) {
            Integer count = map.get(key);
            if (count != 1) {
                return key;
            }
        }
        return null;
    }

}
