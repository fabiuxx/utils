/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.jsf.resources.types.CacheableResourceDecorator;
import fa.gs.utils.jsf.resources.types.EmptyResource;
import fa.gs.utils.misc.text.Text;
import java.util.Map;
import java.util.Objects;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class ResourcesResolver extends ResourceHandlerWrapper {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * ver {@link javax.faces.application.ResourceHandler}.
     */
    private final ResourceHandler wrapped;

    /**
     * Coleccion de matcher de recursos.
     */
    private final Map<String, ResourceMatcher> matchers;
    //</editor-fold>

    /**
     * Constructor.
     *
     * @param wrapped ver {@link javax.faces.application.ResourceHandler}.
     */
    public ResourcesResolver(ResourceHandler wrapped) {
        super();
        this.wrapped = wrapped;
        this.matchers = Maps.empty();
    }

    /**
     * Permite agregar un matcheador de recursos personalizado.
     *
     * @param matcher Matcheador de recursos.
     */
    protected final void addResourceMatcher(ResourceMatcher matcher) {
        if (matcher != null) {
            matchers.put(matcher.getLibraryName(), matcher);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ResourceHandler getWrapped() {
        return wrapped;
    }

    /**
     * Indica si ste administrador de recursos es capaz de servir recursos de la
     * libreria cuyo nombre se indica.
     *
     * @param libraryName Nombre de libreria.
     * @return Si la libreria es soportada.
     */
    private boolean isSupportedLibrary(String libraryName) {
        for (ResourceMatcher matcher : matchers.values()) {
            if (Objects.equals(libraryName, matcher.getLibraryName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Resource createResource(String resourceName, String libraryName) {
        resourceName = Text.normalizeSlashes(resourceName);
        libraryName = Text.normalizeSlashes(libraryName);

        Resource res;
        if (isSupportedLibrary(libraryName)) {
            // Obtener el recurso utilizando un creador de recursos personalizado.
            res = createResourceCustom(resourceName, libraryName);
        } else {
            // Obtener el recurso utilizando el administrador de recursos por defecto.
            res = createResourceDefault(resourceName, libraryName);
        }

        return res;
    }

    private Resource createResourceCustom(String resourceName, String libraryName) {
        Resource res;
        if (matchers.containsKey(libraryName)) {
            ResourceMatcher matcher = matchers.get(libraryName);
            res = matcher.create(this, resourceName, libraryName);
        } else {
            res = EmptyResource.instance(libraryName, resourceName);
        }

        return res;
    }

    public Resource createResourceDefault(String resourceName, String libraryName) {
        Resource res = super.createResource(resourceName, libraryName);
        if (res != null) {
            res = CacheableResourceDecorator.instance(res);
        }
        return res;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean libraryExists(final String libraryName) {
        if (isSupportedLibrary(libraryName)) {
            return true;
        }

        return super.libraryExists(libraryName);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isResourceRequest(final FacesContext context) {
        return super.isResourceRequest(context);
    }

}
