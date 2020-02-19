/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection.names;

import fa.gs.utils.injection.JndiNameGenerator;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JndiPortableGlobalNameGenerator implements JndiNameGenerator {

    private final String earModuleName;

    private final String ejbModuleName;

    public JndiPortableGlobalNameGenerator(String earModuleName, String ejbModuleName) {
        this.earModuleName = earModuleName;
        this.ejbModuleName = ejbModuleName;
    }

    @Override
    public String getJndiName(Class<?> klass) {
        String simpleName = klass.getSimpleName();
        return String.format("java:global/%s/%s/%s", getEarModuleName(), getEjbModuleName(), simpleName);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getEarModuleName() {
        return earModuleName;
    }

    public String getEjbModuleName() {
        return ejbModuleName;
    }
    //</editor-fold>    

}
