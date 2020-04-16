/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf;

import fa.gs.utils.misc.Ids;
import fa.gs.utils.misc.text.Strings;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsfMessagesContainer {

    private final String id;
    private boolean hasMessages;

    private JsfMessagesContainer(String id) {
        this.id = id;
        this.hasMessages = false;
    }

    public static JsfMessagesContainer instance() {
        String id = Ids.randomUuid();
        return instance(id);
    }

    public static JsfMessagesContainer instance(String id) {
        return new JsfMessagesContainer(id);
    }

    public void reset() {
        this.hasMessages = false;
        Jsf.clearMessages(id);
    }

    public void pushError(String fmt, Object... args) {
        String msg = Strings.format(fmt, args);
        FacesMessage fmsg = Jsf.msg().error(msg).build();
        addMessage(fmsg);

    }

    private void addMessage(FacesMessage msg) {
        hasMessages = true;
        Jsf.getFacesContext().addMessage(id, msg);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter y Setters">
    public String getId() {
        return id;
    }

    public boolean getHasMessages() {
        return hasMessages;
    }

    public void setHasMessages(boolean hasMessages) {
        this.hasMessages = hasMessages;
    }
    //</editor-fold>

}
