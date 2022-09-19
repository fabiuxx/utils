/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.crud;

import fa.gs.utils.database.jpa.Jpa;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.StringBuilder2;
import fa.gs.utils.misc.text.Strings;
import java.math.BigInteger;
import javax.persistence.EntityManager;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Eliminar {

    public static final String META_ELIMINADO_FIELD_NAME = "meta__eliminado";

    public static final String META_FECHA_BAJA_FIELD_NAME = "meta__fecha_baja";

    public static Long softDelete(EntityManager em, String schema, String table, String refColumn, BigInteger[] refValues) {
        // Control de seguridad.
        if (Assertions.isNullOrEmpty(refValues)) {
            return 0L;
        }

        // Construir query.
        StringBuilder2 builder = new StringBuilder2();
        builder.append(" UPDATE ");
        builder.append("   \"%s\".\"%s\" ", schema, table);
        builder.append(" SET ");
        builder.append("   \"%s\" = true, ", Eliminar.META_ELIMINADO_FIELD_NAME);
        builder.append("   \"%s\" = now() ", Eliminar.META_FECHA_BAJA_FIELD_NAME);
        builder.append(" WHERE ");
        builder.append("   %s in (%s) ", refColumn, Joiner.of(refValues).separator(",").join());
        builder.append(" ; ");

        // Ejecutar query.
        String query = builder.toString();
        return Jpa.executeUpdate(query, em);
    }

    public static Long hardDelete(EntityManager em, String schema, String table, String refColumn, BigInteger[] refValues) {
        // Control de seguridad.
        if (Assertions.isNullOrEmpty(refValues)) {
            return 0L;
        }

        String sql = Strings.format("DELETE FROM \"%s\".\"%s\" WHERE %s in (%s);", schema, table, refColumn, Joiner.of(refValues).separator(",").join());
        return Jpa.executeUpdate(sql, em);
    }

}
