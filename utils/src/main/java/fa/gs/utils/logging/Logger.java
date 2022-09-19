/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Logger {

    /**
     * Establece el FQCN - Fully Qualified Class Name, que permite identificar
     * los puntos es que es utilizado el sub-sistema de logging dentro de la
     * pila de llamadas.
     *
     * @param fqcn FQCN.
     */
    public void setFqcn(String fqcn);

    /**
     * Obtiene el valor de FQCN actual.
     *
     * @return FQCN
     */
    public String getFqcn();

    /**
     * Obtiene la abstraccion de contexto de diagnostico.
     *
     * @return Contexto de diagnostico.
     */
    public LoggerContext getContext();

    /**
     * Logea eventos de tipo TRACE.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     */
    public void trace(String fmt, Object... args);

    /**
     * Logea eventos de tipo TRACE.
     *
     * @param thr Error capturado.
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     */
    public void trace(Throwable thr, String fmt, Object... args);

    /**
     * Logea eventos tipo DEBUG.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     */
    public void debug(String fmt, Object... args);

    /**
     * Logea eventos tipo DEBUG.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     * @param thr Error capturado.
     */
    public void debug(Throwable thr, String fmt, Object... args);

    /**
     * Logea eventos tipo INFO.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     */
    public void info(String fmt, Object... args);

    /**
     * Logea eventos tipo INFO.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     * @param thr Error capturado.
     */
    public void info(Throwable thr, String fmt, Object... args);

    /**
     * Logea eventos tipo WARNING.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     */
    public void warning(String fmt, Object... args);

    /**
     * Logea eventos tipo WARNING.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     * @param thr Error capturado.
     */
    public void warning(Throwable thr, String fmt, Object... args);

    /**
     * Logea eventos tipo ERROR.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     */
    public void error(String fmt, Object... args);

    /**
     * Logea eventos tipo ERROR.
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     * @param thr Error capturado.
     */
    public void error(Throwable thr, String fmt, Object... args);

    /**
     * Logea eventos tipo ERROR
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     */
    public void fatal(String fmt, Object... args);

    /**
     * Logea eventos tipo ERROR
     *
     * @param fmt Formato de mensaje.
     * @param args Argumentos para formato de mensaje.
     * @param thr Error capturado.
     */
    public void fatal(Throwable thr, String fmt, Object... args);

}
