/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.injection.Lookup;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.misc.text.Text;
import fa.gs.utils.result.simple.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationCase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Components;
import org.omnifaces.util.Faces;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Jsf {

    private static BeanManager beanManager = null;

    public static BeanManager getBeanManager() {
        if (Jsf.beanManager == null) {
            try {
                Result<BeanManager> resInjection = Lookup.withJNDI("java:comp/BeanManager");
                resInjection.raise();
                BeanManager beanManager0 = resInjection.value();
                Jsf.beanManager = beanManager0;
            } catch (Throwable thr) {
                Errors.dump(System.err, thr, "Ocurrió un error obteniendo bean manager.");
                Jsf.beanManager = null;
            }
        }

        return Jsf.beanManager;
    }

    public static <T> T getManagedBean(String beanName, String className) throws Throwable {
        Class<?> klass = Thread.currentThread().getContextClassLoader().loadClass(className);
        return (T) getManagedBean(beanName, klass);
    }

    public static <T> T getManagedBean(String beanName, Class<T> klass) throws Throwable {
        BeanManager beanManager0 = getBeanManager();
        Set<Bean<?>> beans = beanManager0.getBeans(beanName);
        Bean<? extends Object> resolve = beanManager0.resolve(beans);
        CreationalContext<? extends Object> createCreationalContext = beanManager0.createCreationalContext(resolve);
        Object reference = beanManager0.getReference(resolve, klass, createCreationalContext);
        return klass.cast(reference);
    }

    /**
     * Obtiene el contexto del entorno JSF.
     *
     * @return Contexto JSF.
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Obtiene el contexto externo del entorno JSF.
     *
     * @return Contexto externo.
     */
    public static ExternalContext getExternalContext() {
        FacesContext context = getFacesContext();
        return context.getExternalContext();
    }

    /**
     * Obtiene el contexto de facelets del entorno JSF
     *
     * @return Contexto de facelets.
     */
    public static FaceletContext getFaceletContext() {
        FacesContext context = getFacesContext();
        return getFaceletContext(context);
    }

    /**
     * Obtiene el contexto de facelets del entorno JSF.Fuente:
     * https://github.com/primefaces/primefaces/blob/7.0/src/main/java/org/primefaces/behavior/base/AbstractBehaviorHandler.java#L170.
     *
     * @param context Contexto JSF.
     * @return Contexto de facelets.
     */
    public static FaceletContext getFaceletContext(FacesContext context) {
        Object faceletContext = context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
        if (faceletContext == null) {
            faceletContext = context.getAttributes().get("com.sun.faces.facelets.FACELET_CONTEXT");
        }
        return FaceletContext.class.cast(faceletContext);
    }

    public static String getRequestParameter(String name) {
        return getRequestParameter(name, "");
    }

    public static String getRequestParameter(String name, String fallback) {
        ExternalContext ctx = getExternalContext();
        return getRequestParameter0(ctx, name, fallback);
    }

    /**
     * Permite obtener un parametro enviado desde el cliente en una peticion.
     *
     * @param ctx Contexto JSF.
     * @param name Nombre del parametro a leer.
     * @param fallback Valor por defecto a retornar en caso de que el parametro
     * asociado al nombre no exista.
     * @return Valor del parametro, si hubiere. Caso contrario se retorna el
     * valor de {@code fallback}.
     */
    private static String getRequestParameter0(ExternalContext ctx, String name, String fallback) {
        try {
            Map<String, String> params = ctx.getRequestParameterMap();
            if (params == null) {
                throw new IllegalStateException("No se puede obtener el mapa de parametros.");
            }
            return Maps.get(params, name, fallback);
        } catch (Throwable thr) {
            Errors.dump(System.err, thr, "Ocurrió un error obteniendo parametro de peticion: '%s'", name);
            return fallback;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Navegacion">
    /**
     * Crea una instancia de constructor para URLs de vistas JSF.
     *
     * @return Constructor de URLs.
     */
    public static JsfNavigationOutcomeBuilder buildOutcome() {
        return JsfNavigationOutcomeBuilder.instance();
    }

    /**
     * Resuelve una definicion de outcome a una url concreta.
     *
     * @param ctx Contexto JSF.
     * @param outcome Path relativo en contexto de aplicacion.
     * @return URL concreta.
     */
    public static String resolveOutcome(FacesContext ctx, String outcome) {
        if (Assertions.stringNullOrEmpty(outcome)) {
            return "#";
        }

        ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) ctx.getApplication().getNavigationHandler();
        NavigationCase navigationCase = navigationHandler.getNavigationCase(ctx, null, outcome);
        if (navigationCase == null) {
            return ctx.getExternalContext().encodeActionURL(outcome);
        }

        String viewId = navigationCase.getToViewId(ctx);
        String url = ctx.getApplication().getViewHandler().getBookmarkableURL(ctx, viewId, Maps.empty(), false);
        if (!Assertions.stringNullOrEmpty(url)) {
            return url;
        } else {
            return ctx.getExternalContext().encodeActionURL(url);
        }
    }

    /**
     * Permite realizar una redireccion en el lado cliente, de manera
     * programatica desde el lado del servidor..
     *
     * @param outcome Path relativo a la vista a la que se desea redireccionar.
     * @return Si la redireccion tuvo exito.
     */
    public static boolean redirect(String outcome) {
        // Controlar que el path empiece con el caracter de separacion esperado.
        if (!outcome.startsWith("/")) {
            outcome = "/" + outcome;
        }

        // Normalizar slashes dobles.
        outcome = Text.normalizeSlashes(outcome);

        // Intentar redireccionar.
        try {
            ExternalContext context = Jsf.getExternalContext();
            context.redirect(context.getRequestContextPath() + outcome);
            return true;
        } catch (Throwable thr) {
            Errors.dump(System.err, thr, "Ocurrió un error redireccionando outcome: '%s'", outcome);
            return false;
        }
    }
    //</editor-fold>

    /**
     * Permite enviar un archivo como respuesta a una peticion JSF no ajax.
     *
     * @param file Archivo a procesar.
     * @param fileName Nombre de archivo para el lado del cliente.
     * @param contentLength Tamnanho del archivo en disco.
     * @param contentType MIME Type del archivo.
     * @throws Exception Si no se puede enviar el archivo.
     */
    public static void sendFile(File file, String fileName, int contentLength, String contentType) throws Exception {
        InputStream stream = new FileInputStream(file);
        Jsf.sendFile(stream, fileName, contentLength, contentType);
    }

    /**
     * Permite enviar un archivo como respuesta a una peticion JSF no ajax.
     *
     * @param stream Stream de datos a enviar.
     * @param fileName Nombre de archivo para el lado del cliente.
     * @param contentLength Tamnanho del archivo en disco.
     * @param contentType MIME Type del archivo.
     * @throws Exception Si no se puede enviar el archivo.
     */
    public static void sendFile(InputStream stream, String fileName, int contentLength, String contentType) throws Exception {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try (InputStream input = stream) {
            ExternalContext ec = ctx.getExternalContext();
            ec.responseReset();
            ec.setResponseContentType(contentType);
            ec.setResponseContentLength(contentLength);
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            try (OutputStream output = ec.getResponseOutputStream()) {
                byte[] chunk = new byte[4096];
                int read;
                while ((read = input.read(chunk)) >= 0) {
                    output.write(chunk, 0, read);
                    output.flush();
                }
            }
        }
        ctx.responseComplete();
    }

    /**
     * Permite indicar componentes a redibujar durante el ciclo de procesamiento
     * JSF.
     *
     * @param ids Identificadores de componentes.
     */
    public static void update(String... ids) {
        Ajax.update(ids);
    }

    /**
     * Permite agregar codigo javascript a ejecutar en el cliente al finalizar
     * el ciclo de procesamiento JSF.
     *
     * @param fmt Cadena de formato.
     * @param args Parametros para formato, si hubieren.
     */
    public static void eval(String fmt, Object... args) {
        String code = Strings.format(fmt, args);
        if (Faces.isAjaxRequestWithPartialRendering()) {
            Ajax.oncomplete(code);
        } else {
            String script = Strings.format("$(document).ready(function(){%s;});", code);
            Components.addScript(script);
        }
    }

    public static void clearMessages() {
        clearMessages(null);
    }

    public static void clearMessages(String clientId) {
        try {
            Iterator<FacesMessage> it = (Assertions.stringNullOrEmpty(clientId)) ? getFacesContext().getMessages() : getFacesContext().getMessages(clientId);
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        } catch (Throwable thr) {
            Errors.dump(System.err, thr, "No se pudieron limpiar los mensajes.");
        }
    }

    public static JsfMessageBuilder msg() {
        return new JsfMessageBuilder();
    }

    //<editor-fold defaultstate="collapsed" desc="Gestión de Cookies">
    /**
     * Wrapper para
     * {@link Faces#addResponseCookie(java.lang.String, java.lang.String, int) Faces.addResponseCookie}.
     * * {@inheritDoc }
     */
    public static void addCookie(String name, String value, int maxAge) {
        Faces.addResponseCookie(name, value, maxAge);
    }

    /**
     * Wrapper para
     * {@link Faces#addResponseCookie(java.lang.String, java.lang.String, java.lang.String, int) Faces.addResponseCookie}.
     * {@inheritDoc }
     */
    public static void addCookie(String name, String value, String path, int maxAge) {
        Faces.addResponseCookie(name, value, path, maxAge);
    }

    /**
     * Wrapper para
     * {@link Faces#addResponseCookie(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int) Faces.addResponseCookie}.
     * {@inheritDoc }
     */
    public static void addCookie(String name, String value, String domain, String path, int maxAge) {
        Faces.addResponseCookie(name, value, domain, path, maxAge);
    }

    /**
     * Wrapper para
     * {@link Faces#removeResponseCookie(java.lang.String, java.lang.String) Faces.removeResponseCookie}.
     * {@inheritDoc }
     */
    public static void removeCookie(String name, String path) {
        Faces.removeResponseCookie(name, path);
    }
    //</editor-fold>

}
