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
public class Holder<T> {

    private volatile T value;

    private Holder() {
        this.value = null;
    }

    public static Holder instance() {
        return new Holder();
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return get(null);
    }

    public T get(T fallback) {
        if (value == null) {
            return fallback;
        } else {
            return value;
        }
    }

}
