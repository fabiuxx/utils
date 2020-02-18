/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Persistence {

    private DataSource datasource;
    private EntityManagerFactory entityManagerFactory;

    public Persistence() throws Throwable {
        initDatasource();
        initEntityManagerFactory();
    }

    private final void initDatasource() throws Throwable {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("org.postgresql.Driver");
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/sepsa");
        cpds.setUser("sepsa");
        cpds.setPassword("sepsa");
        this.datasource = cpds;
    }

    private final void initEntityManagerFactory() throws Throwable {
        PersistenceUnitInfo persistenceUnitInfo = PersistenceUnit.instance(datasource);
        HibernatePersistenceProvider persistenceProvider = new HibernatePersistenceProvider();
        this.entityManagerFactory = persistenceProvider.createContainerEntityManagerFactory(persistenceUnitInfo, Collections.EMPTY_MAP);
    }

    public void finish() {
        entityManagerFactory.close();
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    private DataSource getDatasource() {
        return datasource;
    }

}
