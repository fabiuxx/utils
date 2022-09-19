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
public abstract class AbstractJndiEnterpriseModuleNameGenerator implements JndiEnterpriseModuleNameGenerator {

    private final String earModuleName;

    private final String ejbModuleName;

    public AbstractJndiEnterpriseModuleNameGenerator(String earModuleName, String ejbModuleName) {
        this.earModuleName = earModuleName;
        this.ejbModuleName = ejbModuleName;
    }

    @Override
    public String getEarModuleName() {
        return earModuleName;
    }

    @Override
    public String getEjbModuleName() {
        return ejbModuleName;
    }

}
