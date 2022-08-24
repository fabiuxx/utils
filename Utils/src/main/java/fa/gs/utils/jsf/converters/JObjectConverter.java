/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.converters;

import com.google.gson.JsonElement;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.json.Json;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Fabio A. González Sosa
 */
@FacesConverter("fags.JObjectConverter")
public class JObjectConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (Assertions.stringNullOrEmpty(value)) {
            return null;
        } else {
            return Json.fromString(value).getAsJsonObject();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            return Json.toString((JsonElement) value);
        }
    }

}
