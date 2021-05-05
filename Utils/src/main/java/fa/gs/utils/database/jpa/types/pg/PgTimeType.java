/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import fa.gs.utils.misc.errors.Errors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgTimeType extends PgScalarType<Date> {

    public PgTimeType() {
        super(Date.class, java.sql.Types.TIME);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Object value = rs.getObject(names[0]);
        if (value == null) {
            return null;
        }

        if (value instanceof java.sql.Time) {
            java.sql.Time sqlDate = (java.sql.Time) value;
            return new Date(sqlDate.getTime());
        }

        throw Errors.unsupported("Instancia de '%s' para columna '%s' no corresponde al tipo 'java.sql.Time'.", value.getClass().getCanonicalName(), names[0]);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, sqlTypes()[0]);
            return;
        }

        if (value instanceof Date) {
            java.sql.Time value0 = new java.sql.Time(((Date) value).getTime());
            st.setTime(index, value0);
            return;
        }

        throw Errors.unsupported("Instancia de '%s' no corresponde al tipo 'java.util.Date'.", value.getClass().getCanonicalName());
    }

}
