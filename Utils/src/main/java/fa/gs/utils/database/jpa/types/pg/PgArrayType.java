/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Units;
import fa.gs.utils.misc.errors.Errors;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class PgArrayType<T> extends PgType {

    public static final int SQL_TYPE = java.sql.Types.ARRAY;

    private final Class<T> javaType;

    private final String sqlTypename;

    public PgArrayType(Class<T> javaType, String sqlTypename) {
        this.javaType = javaType;
        this.sqlTypename = sqlTypename;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{SQL_TYPE};
    }

    @Override
    public Class returnedClass() {
        return List.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Array array = Units.execute(() -> rs.getArray(names[0]));
        if (array == null) {
            return null;
        }

        Object[] javaArray = (Object[]) array.getArray();
        List<T> javaList = Lists.empty();
        if (!Assertions.isNullOrEmpty(javaArray)) {
            for (Object javaArrayElement : javaArray) {
                T javaListElement = nullSafeGetElement(javaArrayElement);
                Lists.add(javaList, javaListElement);
            }
        }
        return javaList;
    }

    protected abstract T nullSafeGetElement(Object arrayElement) throws HibernateException, SQLException;

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, SQL_TYPE);
            return;
        }

        if (value instanceof List) {
            T[] javaArray = Arrays.unwrap((List<T>) value, javaType);
            Connection connection = statement.getConnection();
            Array array = connection.createArrayOf(sqlTypename, javaArray);
            statement.setArray(index, array);
            return;
        }

        throw Errors.unsupported("Instancia de '%s' no corresponde al tipo 'java.util.Date'.", value.getClass().getCanonicalName());

    }

}
