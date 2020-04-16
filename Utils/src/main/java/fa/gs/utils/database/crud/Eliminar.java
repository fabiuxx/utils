/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.crud;

import fa.gs.utils.database.jpa.Jpa;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import java.math.BigInteger;
import javax.persistence.EntityManager;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Eliminar {

    public static final String META_ELIMINADO_FIELD_NAME = "meta__eliminado";

    public Long softDelete(EntityManager em, String schema, String table, BigInteger[] ids) {
        return softDelete(em, schema, table, META_ELIMINADO_FIELD_NAME, ids);
    }

    public Long softDelete(EntityManager em, String schema, String table, String campo, BigInteger[] ids) {
        if (Assertions.isNullOrEmpty(ids)) {
            return 0L;
        }

        String sql = String.format("UPDATE \"%s\".\"%s\" SET \"%s\" = true WHERE id in (%s);", schema, table, campo, Joiner.of(ids).separator(",").join());
        return Jpa.executeUpdate(sql, em);
    }

    public Long hardDelete(EntityManager em, String schema, String table, BigInteger[] ids) {
        if (Assertions.isNullOrEmpty(ids)) {
            return 0L;
        }

        String sql = String.format("DELETE FROM \"%s\".\"%s\" WHERE id in (%s);", schema, table, Joiner.of(ids).separator(",").join());
        return Jpa.executeUpdate(sql, em);
    }

}
