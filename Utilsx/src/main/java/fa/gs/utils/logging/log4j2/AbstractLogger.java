/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.log4j2;

import fa.gs.utils.logging.Logger;
import fa.gs.utils.logging.LoggerContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractLogger extends Log4j2Logger implements Logger {

    /**
     * Contexto de diagnostico.
     */
    private final LoggerContext ctx;

    /**
     * Constructor.
     *
     * @param logger Logger log4j.
     */
    public AbstractLogger(org.apache.logging.log4j.core.Logger logger) {
        super(logger);
        this.ctx = Log4j2LoggerContext.instance();
    }

    @Override
    public LoggerContext getContext() {
        return ctx;
    }

}
