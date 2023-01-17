/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import java.io.Serializable;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class PgType implements UserType, DynamicParameterizedType {

    private Properties properties;

    @Override
    public void setParameterValues(Properties properties) {
        this.properties = properties;
    }

    public Properties getParameterValues() {
        return properties;
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object deepCopy(final Object o) throws HibernateException {
        return o;
    }

    @Override
    public Serializable disassemble(final Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        return x == null ? y == null : x.equals(y);
    }

    @Override
    public int hashCode(final Object o) throws HibernateException {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }

    protected boolean isPosition(String label) {
        return (label.startsWith("[") && label.endsWith("]"));
    }

    protected int getAsPosition(String label) {
        String n = label.substring(1, label.length() - 1);
        return Integer.valueOf(n, 10);
    }
}
