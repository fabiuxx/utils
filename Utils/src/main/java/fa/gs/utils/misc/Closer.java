/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.collections.Lists;
import java.io.Closeable;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Closer {

    private final Collection<Closeable> closeables;
    private final Collection<AutoCloseable> autoCloseables;

    private Closer() {
        this.closeables = Lists.empty();
        this.autoCloseables = Lists.empty();
    }

    public static Closer instance() {
        return new Closer();
    }

    public <T extends Closeable> T add(Closeable closeable) {
        if (closeable != null) {
            closeables.add(closeable);
        }
        return (T) closeable;
    }

    public <T extends AutoCloseable> T add(AutoCloseable closeable) {
        if (closeable != null) {
            autoCloseables.add(closeable);
        }
        return (T) closeable;
    }

    public void close() {
        for (Closeable closeable : closeables) {
            close(closeable);
        }

        for (AutoCloseable closeable : autoCloseables) {
            close(closeable);
        }
    }

    private void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (Throwable thr) {
            ;
        }
    }

    private void close(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Throwable thr) {
            ;
        }
    }

}
