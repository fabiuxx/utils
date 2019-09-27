/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.injection;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.collections.Sets;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Text;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class ResourceInjector implements SystemEventListener {

    private static final String SCRIPT_RESOURCES_KEY = "fa.gs.utils.jsfags.jsf.listeners.AddResourcesListener.ResourceFiles.Script";

    private static final String CSS_RESOURCES_KEY = "fa.gs.utils.jsfags.jsf.listeners.AddResourcesListener.ResourceFiles.Stylesheet";

    private static final String OUTPUT_SCRIPT_RENDERER = "javax.faces.resource.Script";

    private static final String OUTPUT_CSS_RENDERER = "javax.faces.resource.Stylesheet";

    protected abstract String getLibraryName();

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof UIViewRoot);
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();

        // Agregar recursos CSS.
        addStylesheets(context, root);

        // Agregar recursos Javascript.
        addScripts(context, root);

        // Mantenimiento.
        removeDuplicateResources(context, root);
        enforceCorrectLoadOrder(context, root);
    }

    protected void addStylesheets(FacesContext context, UIViewRoot root) {
        Collection<ResourceRequirement> cssResourceRequirements = (Collection<ResourceRequirement>) root.getViewMap().get(CSS_RESOURCES_KEY);
        if (cssResourceRequirements != null) {
            for (ResourceRequirement cssResourceRequirement : cssResourceRequirements) {
                createAndAddComponent(root, context, OUTPUT_CSS_RENDERER, cssResourceRequirement);
            }
        }
    }

    protected void addScripts(FacesContext context, UIViewRoot root) {
        Collection<ResourceRequirement> scriptResourceRequirements = (Collection<ResourceRequirement>) root.getViewMap().get(SCRIPT_RESOURCES_KEY);
        if (scriptResourceRequirements != null) {
            for (ResourceRequirement scriptResourceRequirement : scriptResourceRequirements) {
                createAndAddComponent(root, context, OUTPUT_SCRIPT_RENDERER, scriptResourceRequirement);
            }
        }
    }

    public static void requireStylesheet(String path, String libraryName, Integer priority) {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        if (!viewMap.containsKey(CSS_RESOURCES_KEY)) {
            viewMap.put(CSS_RESOURCES_KEY, Sets.empty());
        }

        ResourceRequirement requirement = new ResourceRequirement(path, libraryName, priority);
        ((Set<ResourceRequirement>) viewMap.get(CSS_RESOURCES_KEY)).add(requirement);
    }

    public static void requireScript(String path, String libraryName, Integer priority) {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        if (!viewMap.containsKey(SCRIPT_RESOURCES_KEY)) {
            viewMap.put(SCRIPT_RESOURCES_KEY, Sets.empty());
        }

        ResourceRequirement requirement = new ResourceRequirement(path, libraryName, priority);
        ((Set<ResourceRequirement>) viewMap.get(SCRIPT_RESOURCES_KEY)).add(requirement);
    }

    private boolean isLibraryComponent(UIComponent component) {
        String library = (String) component.getAttributes().get("library");
        return Assertions.equals(library, getLibraryName());
    }

    private void createAndAddComponent(UIViewRoot root, FacesContext context, String rendererType, ResourceRequirement resourceRequirement) {
        UIOutput output = new UIOutput();
        output.setRendererType(rendererType);
        output.getAttributes().put("name", Text.normalizeSlashes(resourceRequirement.getPath()));
        output.getAttributes().put("library", resourceRequirement.getLibrary());
        output.getAttributes().put("target", "head");
        output.getAttributes().put("data-priority", resourceRequirement.getPriority());

        if (!resourceAlreadyInHead(context, root, output)) {
            root.addComponentResource(context, output, "head");
        }
    }

    private void removeDuplicateResources(FacesContext context, UIViewRoot root) {
        Collection<UIComponent> resourcesToRemove = Lists.empty();
        Map<String, UIComponent> alreadyThere = Maps.empty();

        for (UIComponent resource : getResourcesInHead(context, root)) {
            String key = computeResourceKey(resource);
            if (alreadyThere.containsKey(key)) {
                resourcesToRemove.add(resource);
            } else {
                alreadyThere.put(key, resource);
            }
        }

        removeResourcesFromHead(context, root, resourcesToRemove);
    }

    private void enforceCorrectLoadOrder(FacesContext context, UIViewRoot root) {
        Map<Integer, Collection<UIComponent>> resources = Maps.empty();
        Collection<UIComponent> resourcesToRemove = Lists.empty();

        for (UIComponent resource : getResourcesInHead(context, root)) {
            if (isLibraryComponent(resource)) {
                if (!resource.getAttributes().containsKey("data-priority")) {
                    resource.getAttributes().put("data-priority", ResourceRequirement.MAX_PRIORITY - 1);
                }
                Integer priority = (Integer) resource.getAttributes().get("data-priority");
                Collection<UIComponent> resources0 = resources.getOrDefault(priority, Lists.empty());
                resources0.add(resource);
                resources.put(priority, resources0);
                resourcesToRemove.add(resource);
            }
        }

        removeResourcesFromHead(context, root, resourcesToRemove);
        for (Integer priority : resources.keySet()) {
            Collection<UIComponent> resources0 = resources.get(priority);
            addResourcesToHead(context, root, resources0);
        }
    }

    private Collection<UIComponent> getResourcesInHead(FacesContext context, UIViewRoot root) {
        return root.getComponentResources(context, "head");
    }

    private boolean resourceAlreadyInHead(FacesContext context, UIViewRoot root, UIComponent component) {
        Set<String> keys = getResourcesInHead(context, root).stream()
                .map(r -> computeResourceKey(r))
                .collect(Collectors.toSet());
        String key = computeResourceKey(component);
        return keys.contains(key);
    }

    private String computeResourceKey(UIComponent resource) {
        String url = (String) resource.getAttributes().get("url");
        if (!Assertions.stringNullOrEmpty(url)) {
            return url;
        } else {
            String name = (String) resource.getAttributes().get("name");
            String library = (String) resource.getAttributes().get("library");
            return library + "/" + name + "/" + resource.getClass().getName();
        }
    }

    private void addResourcesToHead(FacesContext context, UIViewRoot root, Collection<UIComponent> components) {
        for (UIComponent component : components) {
            root.addComponentResource(context, component, "head");
        }
    }

    private void removeResourcesFromHead(FacesContext context, UIViewRoot root, Collection<UIComponent> components) {
        for (UIComponent component : components) {
            root.removeComponentResource(context, component, "head");
        }
    }

}
