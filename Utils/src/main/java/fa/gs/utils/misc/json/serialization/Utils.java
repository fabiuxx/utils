/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.serialization;

import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Utils {

    /**
     * Array de tipos que son directamente convertibles por GSON.
     */
    private static final Class<?>[] GSON_PRIMITIVE_TYPES = {
        Byte.class,
        Boolean.class,
        Character.class,
        String.class,
        Short.class,
        Integer.class,
        Long.class,
        Float.class,
        Double.class,
        BigInteger.class,
        BigDecimal.class,
        Void.class
    };

    static boolean isGsonPrimitive(Class<?> type0) {
        for (Class<?> TYPE : GSON_PRIMITIVE_TYPES) {
            if (Objects.equals(type0, TYPE)) {
                return true;
            }
        }
        return false;
    }

    static void checkIsJsonProcessable(Object instance) {
        checkIsJsonProcessable(instance.getClass());
    }

    static void checkIsJsonProcessable(Class sourceClass) {
        Class targetClass = JsonProcessable.class;
        if (!Reflection.isInstanceOf(sourceClass, targetClass)) {
            throw Errors.illegalArgument("La instancia no implementa la interface '%s'.", targetClass.getCanonicalName());
        }
    }

}
