/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.errors;

import java.util.function.Supplier;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Nullsafe {

    public static <T> T get(Supplier<T> supplier) {
        return get(null, supplier);
    }

    public static <T> T get(T fallback, Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable thr) {
            return fallback;
        }
    }

}
