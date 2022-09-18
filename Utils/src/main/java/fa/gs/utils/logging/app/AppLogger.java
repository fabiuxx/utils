/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.app;

import fa.gs.utils.logging.log4j2.Log4j2Logger;
import fa.gs.utils.logging.log4j2.ParametrizedLogger;
import fa.gs.utils.misc.errors.AppErrorException;
import fa.gs.utils.result.utils.Failure;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;

/**
 *
 * @author Fabio A. González Sosa
 */
public class AppLogger extends ParametrizedLogger<AppLogger.Driver> {

    /**
     * Coleccion de tags fijos que son especificos de esta isntancia de logger.
     */
    private final Map<String, String> fixedTags;

    /**
     * Constructor.
     *
     * @param logger Logger concreto para emision de mensajes de log.
     */
    public AppLogger(Logger logger) {
        super(logger);
        fixedTags = new HashMap<>();
    }

    /**
     * Agrega un nuevo parametro fijo a ser incluido dentro del cuerpo del
     * mensaje de log.
     *
     * @param name Nombre del parametro.
     * @param value Valor del parametro.
     * @return Esta misma instancia.
     */
    public AppLogger tag(String name, Object value) {
        this.fixedTags.put(name, String.valueOf(value));
        return this;
    }

    /**
     * Permite registrar el inicio de un trazado de ejecucion.
     *
     * @param traza Valor arbitrario que agrupa a un conjunto de mensajes de
     * log.
     * @return Esta misma instancia.
     */
    public AppLogger inicio(String traza) {
        traza(traza);
        return this;
    }

    /**
     * Permite registrar el fin de un trazado de ejecucion.
     *
     * @param traza Valor arbitrario que agrupa a un conjunto de mensajes de
     * log. Debe coincidir con el utilizado previamente con un llamado a
     * {@link #inicio(java.lang.String) inicio}.
     * @return Esta misma instancia.
     */
    public AppLogger fin(String traza) {
        quitarTraza(traza);
        return this;
    }

    /**
     * Agrega un identificador arbitrario que indica el seguimiento de un camino
     * logico de ejecucion de codigo.
     *
     * @param traza Identificador de traza.
     */
    private void traza(String traza) {
        if (traza != null) {
            Object actual = getContext().peek(AppLogger.Tags.TRAZA);
            if (actual == null) {
                getContext().push(AppLogger.Tags.TRAZA, traza);
            }
        }
    }

    /**
     * Elimina un identificador arbitrario que indica el seguimiento de un
     * camino logico de ejecucion de codigo.
     *
     * @param traza Identificador de traza. Debe coincidir con el utilizado
     * previamente con un llamado a
     * {@link fa.gs.utils.logging.app.AppLogger#traza(java.lang.String) traza}.
     */
    private void quitarTraza(String traza) {
        if (traza != null) {
            Object actual = getContext().peek(AppLogger.Tags.TRAZA);
            if (traza.equals(actual)) {
                getContext().pop(AppLogger.Tags.TRAZA);
            }
        }
    }

    /**
     * Obtiene una instancia del objeto que permite "dirigir" la construccion de
     * mensajes de log.
     *
     * @param logger Logger.
     * @param level Nivel de mensaje.
     * @return Objeto driver.
     */
    @Override
    protected AppLogger.Driver driver(Log4j2Logger logger, Level level) {
        return new AppLogger.Driver(logger, level);
    }

    /**
     * Clase que permite "dirigir" la creacion de una entrada de log conforme a
     * los contratos del logger.
     */
    public static class Driver extends ParametrizedLogger.Driver<AppLogger.Driver> {

        /**
         * Constructor.
         *
         * @param logger Logger.
         * @param level Nivel de mensaje de log.
         */
        protected Driver(Log4j2Logger logger, Level level) {
            super(logger, level);
        }

        /**
         * Obtiene la referencia de logger en uso.
         *
         * @return Logger.
         */
        private AppLogger getLogger() {
            return (AppLogger) logger;
        }

        /**
         * Asigna el identificador de evento de log que se registra.
         *
         * @param idLogEvento Identificador de evento de log.
         * @return Esta misma instancia.
         */
        public AppLogger.Driver evento(Integer idLogEvento) {
            getLogger().getContext().push(AppLogger.Tags.EVENTO, idLogEvento);
            return self();
        }

        /**
         * Permite registrar informacion relacionados a un fallo.
         *
         * @param failure Fallo.
         * @return Esta misma instancia.
         */
        public AppLogger.Driver failure(Failure failure) {
            if (failure != null) {
                cause(failure.cause());
                message(failure.message());
                tags(failure.tags());
            }
            return self();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public AppLogger.Driver cause(Throwable thr) {
            super.cause(thr);
            if (thr instanceof AppErrorException) {
                AppErrorException err = (AppErrorException) thr;
                message(err.message());
                tag("errno", err.errno());
                tags(err.tags());
            }
            return self();
        }

        /**
         * Emite el mensaje de log a las capas concretas de mensajes de
         * diagnostico.
         */
        @Override
        @SuppressWarnings("unchecked")
        public final void log() {
            msg.putAll(getLogger().fixedTags);
            msg.putAll(getLogger().getContext().peekAll());
            logger.log(level, error, msg);
        }

    }

    /**
     * Define algunos tags comunes.
     */
    public static final class Tags {

        public static final String TRAZA = "traza";
        public static final String MENSAJE = "mensaje";
        public static final String EVENTO = "evento";
    }

}
