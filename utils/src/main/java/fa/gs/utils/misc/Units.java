/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Units {

    public static <U extends Unit<T>, T> T execute(U unit) {
        return execute(null, unit);
    }

    public static <U extends Unit<T>, T> T execute(T fallback, U unit) {
        try {
            T value = unit.execute();
            return (value != null) ? value : fallback;
        } catch (Throwable thr) {
            return fallback;
        }
    }

    public static Throwable wrap(Runnable r) {
        try {
            r.run();
            return null;
        } catch (Throwable thr) {
            return thr;
        }
    }

}
