/*
 * The MIT License
 *
 * Copyright 2023 Fabio A. González Sosa.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fa.gs.utils.database.jpa.types.pg;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.arrays.Array2D;
import static fa.gs.utils.database.jpa.types.pg.PgArrayType.SQL_TYPE;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Units;
import fa.gs.utils.misc.errors.Errors;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgTimeArray2DType extends PgType {

    public static final String QNAME = "fa.gs.utils.database.jpa.types.pg.PgTimeArray2DType";

    private final Class<?> klass;

    public PgTimeArray2DType() {
        this.klass = Array2D.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{PgArrayType.SQL_TYPE};
    }

    @Override
    public Class returnedClass() {
        return klass;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Array array = Units.execute(() -> isPosition(names[0]) ? rs.getArray(getAsPosition(names[0])) : rs.getArray(names[0]));
        if (array == null) {
            return null;
        }

        /**
         * NOTE: APARENTEMENTE POSTGRESQL NO TIENE SOPORTE PARA ARRAY
         * MULTIDIMENSIONALES, POR LO TANTO SE ASUME QUE EL LAYOU NATIVO ES UN
         * ARRAY DE Nx2 ELEMENTOS (MATRIZ).
         */
        Object[] javaArray = (Object[]) array.getArray();
        if (javaArray.length % 2 != 0) {
            throw new SQLException("El array de origen no presenta una cantidad de elementos múñltiplo de dos (2).");
        }

        int rows = javaArray.length / 2;
        int cols = 2;
        Array2D<Time> array2d = new Array2D<>(rows, cols);
        if (!Assertions.isNullOrEmpty(javaArray)) {
            for (int i = 0, j = 0; i < javaArray.length; i += 2, j++) {
                array2d.set(j, 0, (Time) nullSafeGetElemet(javaArray, i));
                array2d.set(j, 1, (Time) nullSafeGetElemet(javaArray, i + 1));
            }
        }

        return array2d;
    }

    private Object nullSafeGetElemet(Object[] javaArray, int pos) {
        if (pos >= 0 && pos < javaArray.length) {
            return javaArray[pos];
        } else {
            return null;
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, SQL_TYPE);
            return;
        }

        if (value instanceof Array2D) {
            // "Aplanar" lista 2-dimensional.
            List<Time> list = Lists.empty();
            Array2D<Time> array2d = (Array2D<Time>) value;
            int rows = array2d.getRows();
            int cols = array2d.getColumns();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    list.add(array2d.get(i, j));
                }
            }

            // Guardar como array de 1 sola dimension.
            Connection connection = statement.getConnection();
            Array array = connection.createArrayOf("time", Arrays.unwrap(list, Time.class));
            statement.setArray(index, array);
            return;
        }

        throw Errors.unsupported("Instancia de '%s' no corresponde al tipo '%s'.", value.getClass().getCanonicalName(), Array2D.class.getCanonicalName());
    }

}
