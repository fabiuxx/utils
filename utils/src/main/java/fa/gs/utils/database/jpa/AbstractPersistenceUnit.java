/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa;

import fa.gs.utils.collections.Lists;
import java.net.URL;
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
public abstract class AbstractPersistenceUnit implements PersistenceUnitInfo {

    private final Properties props;

    private final DatasourceProvider datasourceProvider;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbstractPersistenceUnit(DatasourceProvider datasourceProvider) {
        this.datasourceProvider = datasourceProvider;
        this.props = new Properties();
        fillProperties(props);
    }

    protected abstract void fillProperties(Properties props);

    @Override
    public Properties getProperties() {
        return props;
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
        return PersistenceUnitTransactionType.JTA;
    }

    @Override
    public DataSource getJtaDataSource() {
        return datasourceProvider.getJtaDataSource();
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return datasourceProvider.getNonJtaDataSource();
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
