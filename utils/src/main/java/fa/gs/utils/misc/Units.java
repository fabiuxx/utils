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

    @Deprecated
    public static <U extends Unit<T>, T> T execute(U unit) {
        return executeFn(unit);
    }

    @Deprecated
    public static <U extends Unit<T>, T> T execute(T fallback, U unit) {
        return executeFn(fallback, unit);
    }

    @Deprecated
    public static <U extends Unit<T>, T> Throwable capture(U unit) {
        return guardFn(unit);
    }

    public static <U extends UnitFn<T>, T> T executeFn(U unit) {
        return executeFn(null, unit);
    }

    public static <U extends UnitFn<T>, T> T executeFn(T fallback, U unit) {
        try {
            T value = unit.execute();
            return (value != null) ? value : fallback;
        } catch (Throwable thr) {
            return fallback;
        }
    }

    public static <U extends UnitFn<T>, T> Throwable guardFn(U unit) {
        try {
            unit.execute();
            return null;
        } catch (Throwable thr) {
            return thr;
        }
    }

    public static <U extends UnitSub<?>> void executeSub(U unit) {
        try {
            unit.execute();
        } catch (Throwable thr) {
            ;
        }
    }

    public static <U extends UnitSub<T>, T> Throwable guardSub(U unit) {
        try {
            unit.execute();
            return null;
        } catch (Throwable thr) {
            return thr;
        }
    }

}
