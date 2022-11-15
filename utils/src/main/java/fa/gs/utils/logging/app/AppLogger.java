/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.app;

import fa.gs.utils.misc.text.Strings;
import org.slf4j.Logger;

/**
 *
 * @author Fabio A. González Sosa
 */
public class AppLogger {

    private final Logger wrapped;

    public AppLogger(Logger wrapped) {
        this.wrapped = wrapped;
    }

    public void info(String fmt, Object... args) {
        info(null, fmt, args);
    }

    public void info(Throwable thr, String fmt, Object... args) {
        wrapped.info(Strings.format(fmt, args), thr);
    }

    public void debug(String fmt, Object... args) {
        debug(null, fmt, args);
    }

    public void debug(Throwable thr, String fmt, Object... args) {
        wrapped.debug(Strings.format(fmt, args), thr);
    }

    public void warning(String fmt, Object... args) {
        warning(null, fmt, args);
    }

    public void warning(Throwable thr, String fmt, Object... args) {
        wrapped.warn(Strings.format(fmt, args), thr);
    }

    public void error(String fmt, Object... args) {
        error(null, fmt, args);
    }

    public void error(Throwable thr, String fmt, Object... args) {
        wrapped.error(Strings.format(fmt, args), thr);
    }

    public void trace(String fmt, Object... args) {
        trace(null, fmt, args);
    }

    public void trace(Throwable thr, String fmt, Object... args) {
        wrapped.trace(Strings.format(fmt, args), thr);
    }

}
