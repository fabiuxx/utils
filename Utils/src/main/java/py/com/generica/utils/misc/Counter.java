/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Counter {

    private final int initialCount;
    private int count;

    private Counter(int count) {
        this.initialCount = count;
        this.count = count;
    }

    public static final Counter instance(int initialCount) {
        return new Counter(initialCount);
    }

    public synchronized int reset() {
        this.count = initialCount;
        return value();
    }

    public int value() {
        return count;
    }

    public int at(Number inc) {
        int count0 = count + inc.intValue();
        return count0;
    }

    public int inc() {
        return inc(1L);
    }

    public int inc(Number inc) {
        this.count = at(inc);
        return value();
    }

}
