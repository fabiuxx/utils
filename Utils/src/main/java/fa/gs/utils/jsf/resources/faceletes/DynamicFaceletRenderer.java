/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.faceletes;

import java.util.Map;
import javax.faces.view.facelets.FaceletContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface DynamicFaceletRenderer {

    boolean canRender(String resourceName);

    String render(FaceletContext ctx, String resourceName, Map<String, String> params) throws Throwable;

}
