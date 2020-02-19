/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection.names;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JndiFullPortableGlobalNameGenerator extends JndiPortableGlobalNameGenerator {

    public JndiFullPortableGlobalNameGenerator(String earModuleName, String ejbModuleName) {
        super(earModuleName, ejbModuleName);
    }

    @Override
    public String getJndiName(Class<?> klass) {
        String simpleName = klass.getSimpleName();
        String fullName = klass.getCanonicalName();
        return String.format("java:global/%s/%s/%s!%s", getEarModuleName(), getEjbModuleName(), simpleName, fullName);
    }

}
