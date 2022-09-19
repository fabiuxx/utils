/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection.names;

/**
 * Permite generar nombres JNDI que pueden ser utilizados para el descubrimiento
 * de beans remotos mediante las herramientas de jboss. Fuente:
 * https://docs.jboss.org/author/display/WFLY10/EJB+invocations+from+a+remote+client+using+JNDI.
 *
 * @author Fabio A. González Sosa
 */
public class JndiWildflyRemotingNameGenerator extends JndiPortableGlobalNameGenerator {

    public JndiWildflyRemotingNameGenerator(String earModuleName, String ejbModuleName) {
        this(earModuleName, ejbModuleName, true);
    }

    public JndiWildflyRemotingNameGenerator(String earModuleName, String ejbModuleName, boolean useQualifiedName) {
        super(earModuleName, ejbModuleName, useQualifiedName);
    }

    @Override
    public String getNamespace() {
        return "ejb:";
    }

}
