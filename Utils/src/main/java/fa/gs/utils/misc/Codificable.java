/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.misc.errors.Errors;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Sergio D. Riveros Vazquez
 */
public interface Codificable extends Serializable {

    public static <T extends Codificable> T fromCodigo(String codigo, T[] values) {
        for (T value : values) {
            if (Objects.equals(codigo, value.codigo())) {
                return value;
            }
        }
        throw Errors.illegalArgument();
    }

    public static <T extends Enum<T> & Codificable> T fromCodigo(String codigo, Class<T> klass) {
        return fromCodigo(codigo, klass.getEnumConstants());
    }

    String codigo();

    String descripcion();

}
