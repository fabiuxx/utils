/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.build;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SqlClean {

    /**
     * Permite aplicar una serie de operaciones de limpieza sobre una query
     * construida arbitrariamente.
     *
     * @param sql Query.
     * @return Query sanitizada.
     */
    public static String sanitizeQuery(String sql) {
        /**
         * Eliminar parentesis iniciales, si hubieren. Fuente:
         * https://hibernate.atlassian.net/browse/HHH-7962
         */
        if (sql.startsWith("(") && sql.endsWith(")")) {
            sql = sql.substring(1, Math.max(0, sql.length() - 1));
        }
        return sql;
    }

}
