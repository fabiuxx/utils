/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.Message;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class Log4j2Logger {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Servicio de logueo proveido por LOG4J.
     */
    protected final org.apache.logging.log4j.core.Logger logger;

    /**
     * Nombre de la case de este logger. Esta variable es utilizada para que
     * log4j pueda determinar el metodo, archivo y/o numero de linea que llama
     * al servicio de log examinando el stack trace.
     */
    private String FQCN;
    //</editor-fold>

    /**
     * Constructor.
     *
     * @param logger Logger log4j.
     */
    public Log4j2Logger(org.apache.logging.log4j.core.Logger logger) {
        this.logger = logger;
        this.FQCN = getClass().getName();
    }

    /**
     * Establece el FQCN - Fully Qualified Class Name, que permite identificar
     * los puntos es que es utilizado el sub-sistema de logging dentro de la
     * pila de llamadas.
     *
     * @param fqcn FQCN.
     */
    public void setFqcn(String fqcn) {
        this.FQCN = fqcn;
    }

    /**
     * Obtiene el valor de FQCN actual.
     *
     * @return FQCN
     */
    public String getFqcn() {
        return getClass().getName();
    }

    /**
     * Retorna la instancia subyacente que implementa el registro de mensajes de
     * diagnostico.
     *
     * @return Logger nativo.
     */
    public org.apache.logging.log4j.core.Logger getLoggerImpl() {
        return logger;
    }

    /**
     * Logea eventos de cualquier tipo.
     *
     * @param level Nivel de logeo.
     * @param thr Error capturado.
     * @param msg Mensaje.
     */
    public void log(Level level, Throwable thr, Message msg) {
        logger.logIfEnabled(FQCN, level, null, msg, thr);
    }

    /**
     * Logea eventos de tipo TRACE.
     *
     * @param msg Mensaje.
     */
    public void trace(Message msg) {
        log(Level.TRACE, null, msg);
    }

    /**
     * Logea eventos de tipo TRACE.
     *
     * @param thr Error capturado.
     * @param msg Mensaje.
     */
    public void trace(Throwable thr, Message msg) {
        log(Level.TRACE, thr, msg);
    }

    /**
     * Logea eventos tipo DEBUG.
     *
     * @param msg Mensaje.
     */
    public void debug(Message msg) {
        log(Level.DEBUG, null, msg);
    }

    /**
     * Logea eventos tipo DEBUG.
     *
     * @param msg Mensaje.
     * @param thr Error capturado.
     */
    public void debug(Throwable thr, Message msg) {
        log(Level.DEBUG, thr, msg);
    }

    /**
     * Logea eventos tipo INFO.
     *
     * @param msg Mensaje.
     */
    public void info(Message msg) {
        log(Level.INFO, null, msg);
    }

    /**
     * Logea eventos tipo INFO.
     *
     * @param msg Mensaje.
     * @param thr Error capturado.
     */
    public void info(Throwable thr, Message msg) {
        log(Level.INFO, thr, msg);
    }

    /**
     * Logea eventos tipo WARNING.
     *
     * @param msg Mensaje.
     */
    public void warning(Message msg) {
        log(Level.WARN, null, msg);
    }

    /**
     * Logea eventos tipo WARNING.
     *
     * @param msg Mensaje.
     * @param thr Error capturado.
     */
    public void warning(Throwable thr, Message msg) {
        log(Level.WARN, thr, msg);
    }

    /**
     * Logea eventos tipo ERROR.
     *
     * @param msg Mensaje.
     */
    public void error(Message msg) {
        log(Level.ERROR, null, msg);
    }

    /**
     * Logea eventos tipo ERROR.
     *
     * @param msg Mensaje.
     * @param thr Error capturado.
     */
    public void error(Throwable thr, Message msg) {
        log(Level.ERROR, thr, msg);
    }

    /**
     * Logea eventos tipo ERROR
     *
     * @param msg Mensaje.
     */
    public void fatal(Message msg) {
        log(Level.FATAL, null, msg);
    }

    /**
     * Logea eventos tipo ERROR
     *
     * @param msg Mensaje.
     * @param thr Error capturado.
     */
    public void fatal(Throwable thr, Message msg) {
        log(Level.FATAL, thr, msg);
    }

}
