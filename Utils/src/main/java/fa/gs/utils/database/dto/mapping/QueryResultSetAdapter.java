/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.mapping;

import java.util.Collection;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface QueryResultSetAdapter {

    Collection<Map<String, Object>> select(String sql, EntityManager em) throws Throwable;

}
