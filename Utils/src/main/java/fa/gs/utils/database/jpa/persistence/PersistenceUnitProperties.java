/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.persistence;

import java.util.Properties;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PersistenceUnitProperties {

    public static void enableHibernateForPostgresql94(Properties props) {
        props.put("hibernate.show_sql", true);
        props.put("hibernate.format_sql", true);
        props.put("hibernate.generate_statistics", true);
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
    }

    public static void enableGlassfishJTAManager(Properties props) {
        props.put("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.SunOneJtaPlatform");
    }

    public static void enableWildflyJTAManager(Properties props) {
        props.put("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform");
    }

}
