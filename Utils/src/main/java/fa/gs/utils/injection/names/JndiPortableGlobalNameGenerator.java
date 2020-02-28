/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection.names;

import fa.gs.utils.misc.text.Text;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JndiPortableGlobalNameGenerator extends AbstractJndiEnterpriseModuleNameGenerator {

    private final boolean useQualifiedName;

    public JndiPortableGlobalNameGenerator(String earModuleName, String ejbModuleName) {
        this(earModuleName, ejbModuleName, false);
    }

    public JndiPortableGlobalNameGenerator(String earModuleName, String ejbModuleName, boolean useQualifiedName) {
        super(earModuleName, ejbModuleName);
        this.useQualifiedName = useQualifiedName;
    }

    @Override
    public String getNamespace() {
        return "java:global";
    }

    @Override
    public String getJndiName(Class<?> klass) {
        String simpleName = klass.getSimpleName();
        String globalName = String.format("%s/%s/%s/%s", getNamespace(), getEarModuleName(), getEjbModuleName(), simpleName);
        if (useQualifiedName) {
            globalName = globalName + "!" + klass.getCanonicalName();
        }
        return Text.normalizeSlashes(globalName);
    }

}
