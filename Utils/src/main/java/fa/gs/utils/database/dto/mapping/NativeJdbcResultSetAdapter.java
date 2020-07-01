/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.mapping;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Text;
import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import javax.persistence.EntityManager;
import lombok.Data;
import org.hibernate.Session;

/**
 *
 * @author Fabio A. González Sosa
 */
public class NativeJdbcResultSetAdapter implements QueryResultSetAdapter {

    @Override
    public Collection<Map<String, Object>> select(String sql, EntityManager em) throws Throwable {
        Collection<Map<String, Object>> rows = Lists.empty();

        /**
         * Se obtiene una conexion a la BD desde hibernate. Idealmente, para
         * considerarse 100% nativo (JDBC) se deberia utilizar una instancia de
         * datasource para que sea independiente del "entity manager" obtenido
         * desde la capa JPA.
         */
        Session session = em.unwrap(Session.class);
        session.doWork((Connection conn) -> {
            try (Statement stmt = conn.createStatement()) {
                /**
                 * NOTE: SE IMPRIME LA CONSULTA MANUALMENTE YA QUE LA MISMA NO
                 * PASA POR HIBERNATE, EL CUAL ESTA CONFIGURADO PARA IMPRIMIR
                 * LAS CONSULTAS REALIZADAS.
                 */
                System.out.println(sql);

                // Ejecutar query nativa.
                stmt.execute(sql);
                try (ResultSet rs = stmt.getResultSet()) {
                    // Cantidad de columnas por fila.
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();

                    // Mapear filas.
                    while (rs.next()) {
                        // Adaptar cada columna de la fila.
                        Map<String, Object> row = Maps.empty();
                        for (int i = 1; i <= columnsNumber; i++) {
                            // Determinar nombre de columna.
                            String columnAlias = rsmd.getColumnLabel(i);
                            String columnName = rsmd.getColumnName(i);
                            String key = Text.select(columnAlias, columnName);

                            try {
                                // Determinar valor: escalar o array.
                                JdbcValue value;
                                int jdbcType = rsmd.getColumnType(i);
                                if (jdbcType == java.sql.Types.ARRAY) {
                                    value = getArray(rs, rsmd, i);
                                } else {
                                    value = getScalar(rs, rsmd, i);
                                }

                                row.put(key, value);
                            } catch (Throwable thr) {
                                // Error.
                                Errors.dump(System.err, thr);
                                row.put(key, null);
                            }
                        }

                        // Agregar fila a coleccion de filas adaptadas.
                        rows.add(row);
                    }
                }
            }
        });

        return rows;
    }

    private JdbcValue getScalar(ResultSet rs, ResultSetMetaData rsmd, int columnIndex) throws SQLException {
        Object scalar = rs.getObject(columnIndex);
        if (scalar == null) {
            return null;
        }

        JdbcScalar value = new JdbcScalar();
        value.columnName = rsmd.getColumnName(columnIndex);
        value.columnAlias = rsmd.getColumnLabel(columnIndex);
        value.jdbcType = rsmd.getColumnType(columnIndex);
        value.jdbcTypename = rsmd.getColumnTypeName(columnIndex);
        value.scale = rsmd.getScale(columnIndex);
        value.precission = rsmd.getPrecision(columnIndex);
        value.value = scalar;
        return value;
    }

    private JdbcValue getArray(ResultSet rs, ResultSetMetaData rsmd, int columnIndex) throws SQLException {
        Array array = rs.getArray(columnIndex);
        if (array == null) {
            return null;
        }

        JdbcArray value = new JdbcArray();
        value.columnName = rsmd.getColumnName(columnIndex);
        value.columnAlias = rsmd.getColumnLabel(columnIndex);
        value.jdbcType = array.getBaseType();
        value.jdbcTypename = array.getBaseTypeName();
        value.jdbcArrayType = rsmd.getColumnType(columnIndex);
        value.jdbcArrayTypename = rsmd.getColumnTypeName(columnIndex);
        value.scale = rsmd.getScale(columnIndex);
        value.precission = rsmd.getPrecision(columnIndex);
        value.value = (Object[]) array.getArray();
        return value;
    }

    public static interface JdbcValue<T> extends Serializable {

        int getJdbcType();

        String getJdbcTypename();

        T getValue();

    }

    @Data
    public static class JdbcScalar implements JdbcValue<Object> {

        private String columnName;
        private String columnAlias;
        private int jdbcType;
        private String jdbcTypename;
        private int scale;
        private int precission;
        private Object value;
    }

    @Data
    public static class JdbcArray implements JdbcValue<Object[]> {

        private String columnName;
        private String columnAlias;
        private int jdbcType;
        private String jdbcTypename;
        private int jdbcArrayType;
        private String jdbcArrayTypename;
        private int scale;
        private int precission;
        private Object[] value;
    }

}
