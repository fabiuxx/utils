/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
@FunctionalInterface
public interface Unit<T> {

    static <T> T execute(Unit<T> unit) {
        return execute(null, unit);
    }

    static <T> T execute(T fallback, Unit<T> unit) {
        try {
            T value = unit.execute();
            return (value != null) ? value : fallback;
        } catch (Throwable thr) {
            return fallback;
        }
    }
    
    static void execute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable thr) {
            ;
        }
    }

    T execute() throws Throwable;

}
