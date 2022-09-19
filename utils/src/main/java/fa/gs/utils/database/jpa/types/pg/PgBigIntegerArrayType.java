/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import fa.gs.utils.misc.numeric.Numeric;
import java.math.BigInteger;
import java.sql.SQLException;
import org.hibernate.HibernateException;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgBigIntegerArrayType extends PgArrayType<BigInteger> {

    public static final String QNAME = "fa.gs.utils.database.jpa.types.pg.PgBigIntegerArrayType";

    public PgBigIntegerArrayType() {
        super(BigInteger.class, "int8");
    }

    @Override
    protected BigInteger nullSafeGetElement(Object arrayElement) throws HibernateException, SQLException {
        if (arrayElement == null) {
            return null;
        }

        return Numeric.adaptAsBigInteger(arrayElement);
    }

}
