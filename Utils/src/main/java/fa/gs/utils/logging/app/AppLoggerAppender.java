/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.app;

import java.io.Serializable;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 *
 * @author Fabio A. González Sosa
 */
@Plugin(name = "AppLoggerAppender", category = "Core", elementType = "appender", printObject = true)
public class AppLoggerAppender extends AbstractAppender {

    /**
     * Instancia singleton.
     */
    private static AppLoggerAppender INSTANCE = null;

    /**
     * Constructor.
     *
     * @param name Nombre del appender.
     * @param filter Filtro a asociar al appender.
     * @param layout Formato para los eventos manejados por este appender.
     * @param ignoreExceptions Indica si las excepciones, producidas durante la
     * ejecucion del appender, seran logueadas o no.
     */
    @SuppressWarnings("deprecation")
    protected AppLoggerAppender(String name, Filter filter, Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @PluginFactory
    public static AppLoggerAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("otherAttribute") String otherAttribute
    ) {
        // Controlar que se haya definido un nombre para el appender.
        if (name == null) {
            LOGGER.error("No name provided for AppLoggerAppender");
            return null;
        }

        // Controlar que exista un layout de formato.
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        // Instanciar appender.
        if (INSTANCE == null) {
            synchronized (AppLoggerAppender.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppLoggerAppender(name, filter, layout, true);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void append(LogEvent e) {
        /*
        try {
            if (facade == null) {
                facade = Injection.lookup(LogFacade.class);
            }

            if (facade == null) {
                System.err.printf("No se pudo inyectar facade en %s", getClass().getCanonicalName());
                return;
            }

            LogEvent event = e.toImmutable();
            Message m = event.getMessage();
            if (m instanceof MapMessage) {
                MapMessage mm = (MapMessage) m;
                Map<String, String> mp = mm.getIndexedReadOnlyStringMap().toMap();
                String origen = mp.remove("origen");
                String traza = mp.remove("traza");
                Character level = e.getLevel().toString().charAt(0);
                StackTraceElement source = e.getSource();
                JsonElement tags = Json.adapt(mp);
                JsonElement error = Json.adapt(e.getThrown());

                Log log = new Log();
                log.setOrigen(origen);
                log.setFechaRegistro(Fechas.now());
                log.setFechaCaptura(Fechas.fromEpoch(event.getInstant().getEpochMillisecond()));
                log.setNivel(level);
                log.setClase((source == null) ? null : source.getClassName());
                log.setLlamador((source == null) ? null : source.getMethodName());
                log.setArchivo((source == null) ? null : source.getFileName());
                log.setLinea((source == null) ? null : source.getLineNumber());
                log.setTraza(traza);
                log.setIdLogEvento(1);
                log.setTags((tags == null) ? null : tags.toString());
                log.setError((error == null) ? null : error.toString());
                facade.create(log);
            }
        } catch (Throwable thr) {
            thr.printStackTrace(System.err);
        }
         */
    }

}
