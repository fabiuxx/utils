/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.collections.Lists;
import java.io.Closeable;
import java.sql.Connection;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Closer {

    private final Collection<Closeable> closeables;
    private final Collection<AutoCloseable> autoCloseables;
    private final Collection<Connection> connections;

    private Closer() {
        this.closeables = Lists.empty();
        this.autoCloseables = Lists.empty();
        this.connections = Lists.empty();
    }

    public static Closer instance() {
        return new Closer();
    }

    public <T extends Closeable> T add(Closeable object) {
        Lists.add(closeables, false, object);
        return (T) object;
    }

    public <T extends AutoCloseable> T add(AutoCloseable object) {
        Lists.add(autoCloseables, false, object);
        return (T) object;
    }

    public Connection add(Connection object) {
        Lists.add(connections, false, object);
        return object;
    }

    public void close() {
        for (Closeable closeable : closeables) {
            close(closeable);
        }

        for (AutoCloseable closeable : autoCloseables) {
            close(closeable);
        }

        for (Connection connection : connections) {
            close(connection);
        }
    }

    private void close(Closeable object) {
        try {
            object.close();
        } catch (Throwable thr) {
            ;
        }
    }

    private void close(AutoCloseable object) {
        try {
            object.close();
        } catch (Throwable thr) {
            ;
        }
    }

    private void close(Connection object) {
        try {
            object.close();
        } catch (Throwable thr) {
            ;
        }
    }

}
