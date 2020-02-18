/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.database;

import fa.gs.utils.collections.Lists;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PersistenceUnit implements PersistenceUnitInfo {

    public static final String DEFAULT_DATASOURCE_NAME = "jdbc/sepsa_postgres_recepcion";

    private final Properties props;

    private final DataSource datasource;

    private PersistenceUnit(DataSource datasource) {
        this.datasource = datasource;
        this.props = new Properties();
        this.props.put("hibernate.show_sql", true);
        this.props.put("hibernate.format_sql", true);
        this.props.put("hibernate.generate_statistics", true);
        this.props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        this.props.put("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.SunOneJtaPlatform");
    }

    public static PersistenceUnitInfo instance(DataSource datasource) {
        return new PersistenceUnit(datasource);
    }

    @Override
    public Properties getProperties() {
        return props;
    }

    @Override
    public List<String> getManagedClassNames() {
        return new LinkedList<>();
    }

    @Override
    public String getPersistenceUnitName() {
        return "AppPU";
    }

    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return datasource;
    }

    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return Lists.empty();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.UNSPECIFIED;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return "2.1";
    }

    @Override
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {
        ;
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }

}
