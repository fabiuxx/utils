/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.Holder;
import fa.gs.utils.misc.Numeric;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Jpa {

    public static Long count(String sql, String countFieldName, EntityManager em) throws Throwable {
        // Ejecutar consulta.
        Collection<Map<String, Object>> resultsSet = select(sql, em);

        // Mapear resultado.
        Map<String, Object> resultSet = Lists.first(resultsSet);
        Object count0 = Maps.get(resultSet, countFieldName);
        return Numeric.adaptAsLong(count0);
    }

    public static Collection<Map<String, Object>> select(String sql, EntityManager em) throws Throwable {
        // Ejecutar la query e indicar que necesitamos mapear el resultset a un mapa, valga la redundancia.
        org.hibernate.Query hibernateQuery = em.createNativeQuery(sql)
                .unwrap(org.hibernate.Query.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Collection<Map<String, Object>>) hibernateQuery.list();
    }

    public static Long executeUpdate(String sql, EntityManager em) {
        // Limpiar query.
        sql = sanitizeQuery(sql);

        // Ejecutar consulta.
        Query q = em.createNativeQuery(sql);
        return (long) q.executeUpdate();
    }

    public static boolean executeBatch(final String[] statements, EntityManager em) throws Throwable {
        Holder<Boolean> result = Holder.instance();

        Session session = em.unwrap(Session.class);
        session.doWork((Connection conn) -> {
            try {
                try (Statement stmt = conn.createStatement()) {
                    // Agregar sentencias a batch.
                    for (String statement : statements) {
                        String sql = sanitizeQuery(statement);
                        stmt.addBatch(sql);
                    }

                    // Ejecutar batch de sentencias.
                    stmt.executeBatch();
                }

                result.set(true);
            } catch (Throwable thr) {
                result.set(false);
            }
        });

        return result.get();
    }

    private static String sanitizeQuery(String sql) {
        sql = sql.trim();

        // Fuente: https://hibernate.atlassian.net/browse/HHH-7962.
        if (sql.startsWith("(") && sql.endsWith(")")) {
            sql = sql.substring(1, Math.max(0, sql.length() - 1));
        }

        return sql;
    }

}
