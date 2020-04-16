/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.persistence;

import fa.gs.utils.database.jpa.AbstractPersistenceUnit;
import fa.gs.utils.database.jpa.DatasourceProvider;
import java.util.Properties;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractGlassfishPersistenceUnit extends AbstractPersistenceUnit {

    public AbstractGlassfishPersistenceUnit(DatasourceProvider datasourceProvider) {
        super(datasourceProvider);
    }

    @Override
    protected void fillProperties(Properties props) {
        PersistenceUnitProperties.enableHibernateForPostgresql94(props);
        PersistenceUnitProperties.enableGlassfishJTAManager(props);
    }

}
