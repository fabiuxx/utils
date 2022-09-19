/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

import com.google.gson.JsonElement;
import fa.gs.utils.misc.json.Json;
import java.sql.SQLException;
import org.postgresql.util.PGobject;

/**
 *
 * @author Fabio A. González Sosa
 */
public class PgJsonbType extends PgJsonType {

    public static final String QNAME = "fa.gs.utils.database.jpa.types.pg.PgJsonbType";

    public PgJsonbType() {
        ;
    }

    @Override
    protected PGobject wrap(JsonElement json) throws SQLException {
        PGobject pgo = new PGobject();
        pgo.setType("jsonb");
        pgo.setValue(Json.toString(json));
        return pgo;
    }

}
