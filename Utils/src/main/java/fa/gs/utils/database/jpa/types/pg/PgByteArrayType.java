/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import java.sql.SQLException;
import org.hibernate.HibernateException;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgByteArrayType extends PgArrayType<Byte> {

    public static final String QNAME = "fa.gs.utils.database.jpa.types.pg.PgByteArrayType";

    public PgByteArrayType() {
        super(Byte.class, "bytea");
    }

    @Override
    protected Byte nullSafeGetElement(Object arrayElement) throws HibernateException, SQLException {
        if (arrayElement == null) {
            return null;
        }

        return (Byte) arrayElement;
    }

}
