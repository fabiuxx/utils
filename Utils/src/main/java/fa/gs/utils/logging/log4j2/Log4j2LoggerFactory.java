/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging.log4j2;

import fa.gs.utils.logging.LoggerFactory;
import java.io.InputStream;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class Log4j2LoggerFactory implements LoggerFactory {

    /**
     * Configura el sub-sistema de logging especifico para Log4j.
     *
     * @param loader Cargador de clases.
     * @param path Camino a archivo XML de configuracion.
     * @return Si la configuracion tuvo exito o no.
     */
    public static boolean configure(ClassLoader loader, String path) {
        try {
            InputStream is = loader.getResourceAsStream(path);
            return configure(is);
        } catch (Throwable thr) {
            synchronized (System.err) {
                System.err.println("ERROR CONFIGURANGO LOGGER FACTORY");
                thr.printStackTrace(System.err);
            }
            return false;
        }
    }

    /**
     * Configura el sub-sistema de logging especifico para Log4j.
     *
     * @param configurationInput Stream de entrada para configuracion XML.
     * @return Si la configuracion tuvo exito o no.
     */
    public static boolean configure(InputStream configurationInput) {
        try ( InputStream is = configurationInput) {
            ConfigurationSource source = new ConfigurationSource(is);
            Configurator.initialize(null, source);
            return true;
        } catch (Throwable thr) {
            synchronized (System.err) {
                System.err.println("ERROR CONFIGURANGO LOGGER FACTORY");
                thr.printStackTrace(System.err);
            }
            return false;
        }
    }

}
