/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection;

import fa.gs.utils.injection.names.JndiSimpleNameGenerator;
import fa.gs.utils.result.simple.Result;
import javax.naming.Context;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Injector {

    private JndiNameGenerator nameGenerator;

    public Injector() {
        this.nameGenerator = new JndiSimpleNameGenerator();
    }

    public <T> Result<T> bean(Context context, Class<T> beanClass) {
        String jndi = nameGenerator.getJndiName(beanClass);
        return Lookup.withJNDI(context, jndi);
    }

    public <T> Result<T> bean(Class<T> beanClass) {
        String jndi = nameGenerator.getJndiName(beanClass);
        return Lookup.withJNDI(jndi);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public JndiNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(JndiNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }
    //</editor-fold>

}
