/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa;

import javax.sql.DataSource;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface DatasourceProvider {

    DataSource getJtaDataSource();

    DataSource getNonJtaDataSource();

}
