/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections;

import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.errors.Errors;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Enums {

    public static <T extends Enum<T> & Codificable> T getCodificable(String codigo, Class<T> enumKlass) {
        for (T enumConstant : enumKlass.getEnumConstants()) {
            if (Objects.equals(enumConstant.codigo(), codigo)) {
                return enumConstant;
            }
        }
        throw Errors.illegalArgument();
    }

}
