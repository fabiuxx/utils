/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import com.google.gson.JsonElement;
import fa.gs.utils.misc.Units;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.json.Json;
import fa.gs.utils.misc.json.JsonElementSerializableWrapper;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java8.util.Objects;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.postgresql.util.PGobject;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgJsonType extends PgScalarType<JsonElementSerializableWrapper> {

    public static final String QNAME = "fa.gs.utils.database.jpa.types.pg.PgJsonType";

    public PgJsonType() {
        super(JsonElementSerializableWrapper.class, java.sql.Types.OTHER);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Object value = rs.getObject(names[0]);
        if (value == null) {
            return null;
        }

        /**
         * Existe un problema con el classloader que interactua directamente con
         * el datasource (definido como un modulo wildfly) y el classloader que
         * carga las dependencias de cada modulo en tiempo de ejecucion. Si se
         * utiliza una dependencia con las clases JDBC correspondientes, la JVM
         * interpretara las mismas como clases diferentes y no se podra realizar
         * un 'casting' de la instancia referenciada por 'value'. De momento,
         * solo se puede manipular mediante reflexion.
         */
        if (Objects.equals(value.getClass().getCanonicalName(), "org.postgresql.util.PGobject")) {
            String value0 = Units.execute(() -> {
                Method mToString = value.getClass().getMethod("toString");
                Object value1 = mToString.invoke(value);
                return String.class.cast(value1);
            });

            JsonElement json = Json.fromString(value0);
            return new JsonElementSerializableWrapper(json);
        }

        throw Errors.unsupported("Instancia de '%s' para columna '%s' no corresponde al tipo 'PGobject'.", value.getClass().getCanonicalName(), names[0]);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, sqlTypes()[0]);
            return;
        }

        if (value instanceof String) {
            st.setObject(index, value);
            return;
        }

        if (value instanceof JsonElementSerializableWrapper) {
            JsonElement json = ((JsonElementSerializableWrapper) value).getJsonElement();
            PGobject value0 = wrap(json);
            st.setObject(index, value0, sqlTypes()[0]);
            return;
        }

        throw Errors.unsupported("Instancia de '%s' no corresponde al tipo 'JsonElementSerializableWrapper'.", value.getClass().getCanonicalName());
    }

    protected PGobject wrap(JsonElement json) throws SQLException {
        PGobject pgo = new PGobject();
        pgo.setType("json");
        pgo.setValue(Json.toString(json));
        return pgo;
    }

}
