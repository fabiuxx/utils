/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources;

import fa.gs.utils.collections.Maps;
import java.util.Map;
import javax.faces.application.Resource;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface ResourceMatcher {

    String getLibraryName();

    default Resource create(ResourcesResolver resolver, String resourceName, String libraryName) {
        Map<String, String> params = Maps.empty();
        return create(resolver, resourceName, libraryName, params);
    }

    Resource create(ResourcesResolver resolver, String resourceName, String libraryName, Map<String, String> params);

}
