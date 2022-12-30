/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.app;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Ids;
import fa.gs.utils.misc.text.Strings;
import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 *
 * @author Fabio A. González Sosa
 */
public class AppLogger {

    private static final String KEY_MDC_TRAZA = "traza";

    private static final String KEY_MDC_TRAZA_RC = "traza.rc";

    private final Logger wrapped;

    public AppLogger(Logger wrapped) {
        this.wrapped = wrapped;
    }

    public String trazaOn() {
        Integer trazaRc = getCurrentTrazaRc();
        String traza = getCurrentTraza();
        MDC.put(KEY_MDC_TRAZA, traza);
        MDC.put(KEY_MDC_TRAZA_RC, String.valueOf(trazaRc + 1));
        return traza;
    }

    private String getCurrentTraza() {
        String traza = MDC.get(KEY_MDC_TRAZA);
        if (Assertions.stringNullOrEmpty(traza)) {
            traza = Ids.randomUuid();
        }
        return traza;
    }

    private Integer getCurrentTrazaRc() {
        String rc = MDC.get(KEY_MDC_TRAZA_RC);
        return Assertions.stringNullOrEmpty(rc) ? 0 : Integer.valueOf(rc, 10);
    }

    public void trazaOff() {
        Integer trazaRc = getCurrentTrazaRc();
        trazaRc--;
        if (trazaRc <= 0) {
            MDC.remove("traza");
        }
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
