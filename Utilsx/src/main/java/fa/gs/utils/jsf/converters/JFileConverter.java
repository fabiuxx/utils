/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.converters;

import com.google.gson.JsonObject;
import fa.gs.utils.collections.Arrays;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.json.Json;
import fa.gs.utils.misc.json.JsonObjectBuilder;
import fa.gs.utils.misc.json.JsonResolver;
import java.util.Base64;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Fabio A. González Sosa
 */
@FacesConverter(JFileConverter.CONVERTER_ID)
public class JFileConverter implements Converter {

    public static final String CONVERTER_ID = "fags.JFileConverter";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (Assertions.stringNullOrEmpty(value)) {
            return null;
        } else {
            JsonObject json = Json.fromString(value).getAsJsonObject();
            FileElement file = new FileElement();
            file.setName(JsonResolver.string(json, "fileName"));
            file.setMimeType(JsonResolver.string(json, "fileType"));
            file.setContent(Base64.getDecoder().decode(JsonResolver.string(json, "fileContent")));
            file.setSize(Arrays.size(file.getContent()));
            return file;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || !(value instanceof FileElement)) {
            return "";
        } else {
            FileElement file = (FileElement) value;
            JsonObjectBuilder ob = JsonObjectBuilder.instance();
            ob.add("fileName", file.getName());
            ob.add("filType", file.getMimeType());
            ob.add("fileContent", (Assertions.isNullOrEmpty(file.getContent()) ? "" : Base64.getEncoder().encodeToString(file.getContent())));
            return Json.toString(ob.build());
        }
    }

}
