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
public interface JndiEnterpriseModuleNameGenerator extends JndiWithNamespaceNameGenerator {

    String getEarModuleName();

    String getEjbModuleName();

}
