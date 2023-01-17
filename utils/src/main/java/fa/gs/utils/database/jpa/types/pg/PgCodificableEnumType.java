/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.Codificables;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Strings;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgCodificableEnumType extends PgScalarType<Codificable> {

    public static final String QNAME = "fa.gs.utils.database.jpa.types.pg.PgCodificableEnumType";

    public static final String PARAM_CODIFICABLE_QNAME = "PgCodificableEnumType.codificableQname";

    public PgCodificableEnumType() {
        super(Codificable.class, java.sql.Types.VARCHAR);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Object value = isPosition(names[0]) ? rs.getObject(getAsPosition(names[0])) : rs.getObject(names[0]);
        if (value == null) {
            return null;
        }

        String paramCodificableQname = getParameterValues().getProperty(PARAM_CODIFICABLE_QNAME);
        if (Assertions.stringNullOrEmpty(paramCodificableQname)) {
            throw Errors.illegalArgument("Se debe definir el QNAME del codificable.");
        }

        if (value instanceof String) {
            try {
                Class codificableClass = Class.forName(paramCodificableQname);
                String codificableValue = (String) value;
                Codificable codificable = (Codificable) Codificables.fromCodigo(codificableValue, codificableClass);
                return codificable;
            } catch (Throwable thr) {
                String msg = Strings.format("No se pudo decodificar '%s'.", paramCodificableQname);
                throw new HibernateException(msg, thr);
            }
        }

        throw Errors.unsupported("Instancia de '%s' para columna '%s' no corresponde al tipo 'fa.gs.utils.misc.Codificable'.", value.getClass().getCanonicalName(), names[0]);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, sqlTypes()[0]);
            return;
        }

        if (value instanceof Codificable) {
            Codificable value0 = (Codificable) value;
            st.setString(index, value0.codigo());
            return;
        }

        throw Errors.unsupported("Instancia de '%s' no corresponde al tipo 'fa.gs.utils.misc.Codificable'.", value.getClass().getCanonicalName());
    }

}
