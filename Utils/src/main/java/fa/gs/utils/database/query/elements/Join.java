/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.misc.text.Strings;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class Join implements QueryPart {

    private Join.Type type;
    private Table table;
    private Expression on;

    public Join(Type type, Table table, Expression on) {
        this.type = type;
        this.table = table;
        this.on = on;
    }

    @Override
    public String stringify(Dialect dialect) {
        if (on != null) {
            return Strings.format("%s %s ON %s", type.keyword, table.stringify(dialect), on.stringify(dialect));
        } else {
            return Strings.format("%s %s", type.keyword, table.stringify(dialect));
        }
    }

    public static enum Type {
        NORMAL("JOIN"),
        INNER("INNER JOIN"),
        LEFT("LEFT JOIN"),
        RIGHT("RIGHT JOIN"),
        LATERAL("LATERAL JOIN");
        private final String keyword;

        Type(String keyword) {
            this.keyword = keyword;
        }

    }
}
