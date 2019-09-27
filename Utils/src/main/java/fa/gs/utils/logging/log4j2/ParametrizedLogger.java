/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.log4j2;

import fa.gs.utils.misc.Assertions;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.MapMessage;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public abstract class ParametrizedLogger<T extends ParametrizedLogger.Driver<T>> extends AbstractLogger {

    /**
     * Constructor.
     *
     * @param logger Logger log4j.
     */
    public ParametrizedLogger(org.apache.logging.log4j.core.Logger logger) {
        super(logger);
    }

    /**
     * Permite emitir mensajes de log con nivel TRACE.
     *
     * @return Driver para especificacion de elementos que componen el mensaje a
     * loguear.
     */
    public T trace() {
        return driver(this, Level.TRACE);
    }

    /**
     * Permite emitir mensajes de log con nivel DEBUG.
     *
     * @return Driver para especificacion de elementos que componen el mensaje a
     * loguear.
     */
    public T debug() {
        return driver(this, Level.DEBUG);
    }

    /**
     * Permite emitir mensajes de log con nivel INFO.
     *
     * @return Driver para especificacion de elementos que componen el mensaje a
     * loguear.
     */
    public T info() {
        return driver(this, Level.INFO);
    }

    /**
     * Permite emitir mensajes de log con nivel WARNING.
     *
     * @return Driver para especificacion de elementos que componen el mensaje a
     * loguear.
     */
    public T warning() {
        return driver(this, Level.WARN);
    }

    /**
     * Permite emitir mensajes de log con nivel ERROR.
     *
     * @return Driver para especificacion de elementos que componen el mensaje a
     * loguear.
     */
    public T error() {
        return driver(this, Level.ERROR);
    }

    /**
     * Permite emitir mensajes de log con nivel FATAL.
     *
     * @return Driver para especificacion de elementos que componen el mensaje a
     * loguear.
     */
    public T fatal() {
        return driver(this, Level.FATAL);
    }

    //<editor-fold defaultstate="collapsed" desc="Implementaciones para compatibilidad con API existente">
    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Deprecated
    @Override
    public void trace(String fmt, Object... args) {
        trace(null, fmt, args);
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void trace(Throwable thr, String fmt, Object... args) {
        trace().cause(thr).message(fmt, args).log();
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void debug(String fmt, Object... args) {
        debug(null, fmt, args);
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void debug(Throwable thr, String fmt, Object... args) {
        debug().cause(thr).message(fmt, args).log();
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void info(String fmt, Object... args) {
        info(null, fmt, args);
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void info(Throwable thr, String fmt, Object... args) {
        info().cause(thr).message(fmt, args).log();
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void warning(String fmt, Object... args) {
        warning(null, fmt, args);
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void warning(Throwable thr, String fmt, Object... args) {
        warning().cause(thr).message(fmt, args).log();
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void error(String fmt, Object... args) {
        error(null, fmt, args);
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void error(Throwable thr, String fmt, Object... args) {
        error().cause(thr).message(fmt, args).log();
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void fatal(String fmt, Object... args) {
        fatal(null, fmt, args);
    }

    /**
     * @deprecated Usar
     * {@link fa.gs.logging.log4j2.impl.ParametrizedLogger#trace() trace}.
     */
    @Override
    public void fatal(Throwable thr, String fmt, Object... args) {
        fatal().cause(thr).message(fmt, args).log();
    }
    //</editor-fold>   

    /**
     * Permite obtener una instancia valida del driver que permitira especificar
     * los elementos que componen el mensaje a loguear.
     *
     * @param logger Logger.
     * @param level Nivel de mensaje de log.
     * @return Driver.
     */
    protected abstract T driver(Log4j2Logger logger, Level level);

    /**
     * Clase que permite "dirigir" la forma en que se emiten los mensajes de
     * log.
     *
     * @param <T>
     */
    public static class Driver<T extends Driver<T>> {

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        /**
         * Logger.
         */
        protected final Log4j2Logger logger;

        /**
         * Mensaje que utiliza un mapa para la organizacion de los datos.
         */
        protected final MapMessage msg;

        /**
         * Nivel de mensaje de log.
         */
        protected Level level;

        /**
         * Error a loguear.
         */
        protected Throwable error;
        //</editor-fold>

        /**
         * Constructor.
         *
         * @param logger Logger.
         * @param level Nivel de mensaje de log.
         */
        protected Driver(Log4j2Logger logger, Level level) {
            this.logger = logger;
            this.level = level;
            this.msg = new MapMessage();
            this.error = null;
        }

        /**
         * Obtiene una referencia de esta misma instancia, con la posibilidad de
         * adaptar el tipo de la instancia.
         *
         * @return Esta misma instancia.
         */
        protected T self() {
            return (T) this;
        }

        /**
         * Establece el nivel de log.
         *
         * @param level Nivel de log.
         * @return Esta misma instancia.
         */
        public T level(Level level) {
            if (level != null) {
                this.level = level;
            }
            return self();
        }

        /**
         * Genera una excepcion utilizando el mensaje indicado y asigna el mismo
         * como cause a loguear.
         *
         * @param fmt Formato de mensaje.
         * @param args Argumentos de mensaje.
         * @return Esta misma instancia.
         */
        public T cause(String fmt, Object... args) {
            String mensaje = String.format(fmt, args);
            return cause(new Exception(mensaje));
        }

        /**
         * Asigna el cause a loguear.
         *
         * @param error Error capturado.
         * @return Esta misma instancia.
         */
        public T cause(Throwable error) {
            this.error = error;
            return self();
        }

        /**
         * Asigna un mensaje personalizado dentro de los parametros que
         * conforman el contenido a loguear.
         *
         * @param fmt Formato de mensaje.
         * @param args Argumentos de mensaje.
         * @return Esta misma instancia.
         */
        public T message(String fmt, Object... args) {
            String mensaje = String.format(fmt, args);
            return tag("mensaje", mensaje);
        }

        /**
         * Asigna una coleccion a parametros al conjunto de parametros que
         * conforman el contenido a loguear.
         *
         * @param values Coleccion de parametros.
         * @return Esta misma instancia.
         */
        public T tags(Map<String, Object> values) {
            if (!Assertions.isNullOrEmpty(values)) {
                msg.putAll(values);
            }
            return self();
        }

        /**
         * Asigna un parametro individual al conjunto de parametros que
         * conforman el contenido a loguear.
         *
         * @param name Nombre de parametro.
         * @param value Valor.
         * @return Esta misma instancia.
         */
        public T tag(String name, Object value) {
            msg.put(name, String.valueOf(value));
            return self();
        }

        /**
         * Construye el mensaje final que sera logueado.
         */
        public void log() {
            logger.log(level, error, msg);
        }
    }

}
