/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Type;
import fa.gs.utils.misc.text.StringTyper;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.misc.text.Text;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import org.omnifaces.util.Ajax;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Jsf {

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
        return getFacesContext().getExternalContext();
    }

    /**
     * Obtiene el contexto de facelets del entorno JSF. Fuente:
     * https://github.com/primefaces/primefaces/blob/7.0/src/main/java/org/primefaces/behavior/base/AbstractBehaviorHandler.java#L170.
     *
     * @param context Contexto JSF.
     * @return Contexti de facelets.
     */
    public static FaceletContext getFaceletContext(FacesContext context) {
        FaceletContext faceletContext = (FaceletContext) context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
        if (faceletContext == null) {
            faceletContext = (FaceletContext) context.getAttributes().get("com.sun.faces.facelets.FACELET_CONTEXT");
        }

        return faceletContext;
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
                throw new IllegalStateException("No se puede obtener el mapa de parametros");
            }
            return Maps.get(params, name, fallback);
        } catch (Throwable thr) {
            return fallback;
        }
    }

    public static <T> T getRequestParameter(String name, Type type) {
        return getRequestParameter(name, type, null);
    }

    public static <T> T getRequestParameter(String name, Type type, T fallback) {
        return getRequestParameter(getFacesContext(), name, type, fallback);
    }

    public static <T> T getRequestParameter(FacesContext ctx, String name, Type type) {
        return getRequestParameter(ctx, name, type, null);
    }

    public static <T> T getRequestParameter(FacesContext ctx, String name, Type type, T fallback) {
        return getRequestParameter(ctx.getExternalContext(), name, type, fallback);
    }

    public static <T> T getRequestParameter(ExternalContext ctx, String name, Type type) {
        return getRequestParameter(ctx, name, type, null);
    }

    public static <T> T getRequestParameter(ExternalContext ctx, String name, Type type, T fallback) {
        String param = getRequestParameter0(ctx, name, null);
        return StringTyper.typeCast(param, type, fallback);
    }

    public static String resolveNavigationOutcome(FacesContext ctx, String outcome) {
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
     * @param path Path relativo a la vista a la que se desea redireccionar.
     */
    public static void redirect(String path) {
        // Controlar que el path empiece con el caracter de separacion esperado.
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        // Normalizar slashes dobles.
        path = Text.normalizeSlashes(path);

        // Intentar redireccionar.
        try {
            ExternalContext context = Jsf.getExternalContext();
            context.redirect(context.getRequestContextPath() + path);
        } catch (Exception e) {
            ;
        }
    }

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

    public static boolean hasMessagesQueued(String clientId) {
        try {
            Iterator it = Jsf.getFacesContext().getMessages(clientId);
            return it.hasNext();
        } catch (Throwable thr) {
            return false;
        }
    }

    public static JsfMessageBuilder msg() {
        return new JsfMessageBuilder();
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
        Ajax.oncomplete(code);
    }

}
