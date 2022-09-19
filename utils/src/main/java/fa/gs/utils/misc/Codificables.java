/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.misc.errors.Errors;
import java.util.Objects;

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

}
