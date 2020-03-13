/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf;

import fa.gs.utils.misc.Builder;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsfMessageBuilder implements Builder<JsfMessageBuilder, FacesMessage> {

    private FacesMessage.Severity severity;
    private String summary;
    private String detail;

    JsfMessageBuilder() {
        this.severity = FacesMessage.SEVERITY_INFO;
        this.summary = null;
        this.detail = null;
    }

    public JsfMessageBuilder severity(FacesMessage.Severity severity) {
        this.severity = severity;
        return self();
    }

    public JsfMessageBuilder info() {
        severity(FacesMessage.SEVERITY_INFO);
        return self();
    }

    public JsfMessageBuilder error() {
        severity(FacesMessage.SEVERITY_ERROR);
        return self();
    }

    public JsfMessageBuilder warning() {
        severity(FacesMessage.SEVERITY_WARN);
        return self();
    }

    public JsfMessageBuilder summary(String summary) {
        this.summary = summary;
        return self();
    }

    public JsfMessageBuilder detail(String detail) {
        this.detail = detail;
        return self();
    }

    public JsfMessageBuilder info(String detail) {
        info();
        detail(detail);
        return self();
    }

    public JsfMessageBuilder warning(String detail) {
        warning();
        detail(detail);
        return self();
    }

    public JsfMessageBuilder error(String detail) {
        error();
        detail(detail);
        return self();
    }

    public JsfMessageBuilder info(String summary, String detail) {
        info(detail);
        summary(summary);
        return self();
    }

    public JsfMessageBuilder warning(String summary, String detail) {
        warning(detail);
        summary(summary);
        return self();
    }

    public JsfMessageBuilder error(String summary, String detail) {
        error(detail);
        summary(summary);
        return self();
    }

    @Override
    public FacesMessage build() {
        FacesMessage msg = new FacesMessage(severity, summary, detail);
        return msg;
    }

    public void push() {
        FacesMessage msg = build();
        Jsf.getFacesContext().addMessage(null, msg);
    }

}
