/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.StringBuilder2;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ClientIds {

    /**
     * Construye un identificador JSF completo en base a identificadores de
     * componentes parciales.
     *
     * @param separator Separador de identificadores a utilizar.
     * @param parts Identificadores parciales.
     * @return Identificador completo.
     */
    public static String id(char separator, String... parts) {
        return Joiner.of(parts)
                .separator(String.valueOf(separator))
                .join();
    }

    /**
     * Construye un identificador JSF completo en base a identificadores de
     * componentes parciales.
     *
     * @param parts Identificadores parciales.
     * @return Identificador completo.
     */
    public static String id(String... parts) {
        return id('-', parts);
    }

    public static String id(FacesContext ctx, UIComponent component) {
        return id(ctx, null, component, null);
    }

    public static String id(FacesContext ctx, String prefix, UIComponent component) {
        return id(ctx, prefix, component, null);
    }

    public static String id(FacesContext ctx, UIComponent component, String postfix) {
        return id(ctx, null, component, postfix);
    }

    public static String id(FacesContext ctx, String prefix, UIComponent component, String postfix) {
        StringBuilder2 id = new StringBuilder2();

        if (!Assertions.stringNullOrEmpty(prefix)) {
            id.append(prefix.trim());
        }

        String clientId = component.getClientId(ctx);
        id.append(clientId);

        if (!Assertions.stringNullOrEmpty(postfix)) {
            id.append(postfix.trim());
        }

        return id.toString().trim();
    }

}
