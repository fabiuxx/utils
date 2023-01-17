/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import java.sql.SQLException;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgDateArrayType extends PgArrayType<Date> {

    public static final String QNAME = "fa.gs.utils.database.jpa.types.pg.PgDateArrayType";

    public PgDateArrayType() {
        super(Date.class, "date");
    }

    @Override
    protected Date nullSafeGetElement(Object arrayElement) throws HibernateException, SQLException {
        if (arrayElement == null) {
            return null;
        }

        return (Date) arrayElement;
    }

}
