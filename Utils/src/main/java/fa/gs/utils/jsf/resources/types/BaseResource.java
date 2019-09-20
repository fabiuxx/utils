/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.types;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.text.Text;
import java.net.URL;
import java.util.Map;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class BaseResource extends Resource {

    /**
     * Constructor.
     *
     * @param libraryName Nombre de la libreria que contiene el recurso.
     * @param resourceName Identificador del recurso, normalmente un nombre.
     */
    protected BaseResource(String libraryName, String resourceName) {
        setLibraryName(libraryName);
        setResourceName(resourceName);
    }

    /**
     * Obtiene el classloader a utilizar en la manipulacion de clases y
     * recursos.
     *
     * @return Classloader.
     */
    protected ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, String> getResponseHeaders() {
        Map<String, String> headers = Maps.empty();
        return headers;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRequestPath() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ViewHandler viewHandler = ctx.getApplication().getViewHandler();
        String path = String.format("%s/%s.xhtml?ln=%s", ResourceHandler.RESOURCE_IDENTIFIER, getResourceName(), getLibraryName());
        return viewHandler.getResourceURL(ctx, Text.normalizeSlashes(path));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public URL getURL() {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean userAgentNeedsUpdate(FacesContext context) {
        return true;
    }

}
