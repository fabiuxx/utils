/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Units;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Joiner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgTextAggregationType extends PgType {

    public static final int SQL_TYPE = java.sql.Types.ARRAY;

    private static final String SEPARATOR = ";";

    public static final String QNAME = "fa.gs.utils.database.jpa.types.pg.PgTextAggregationType";

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
        String text = Units.execute(() -> rs.getString(names[0]));
        if (Assertions.stringNullOrEmpty(text)) {
            return null;
        }

        List<String> javaList = Lists.empty();
        if (!Assertions.stringNullOrEmpty(text)) {
            String[] parts = text.split(Pattern.quote(SEPARATOR));
            if (!Assertions.isNullOrEmpty(parts)) {
                Lists.add(javaList, parts);
            }
        }

        return javaList;
    }

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, SQL_TYPE);
            return;
        }

        if (value instanceof List) {
            String text = Joiner.of((List<String>) value).separator(SEPARATOR).join();
            statement.setString(index, text);
            return;
        }

        throw Errors.unsupported("Instancia de '%s' no corresponde al tipo 'java.util.List'.", value.getClass().getCanonicalName());
    }

}
