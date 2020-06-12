/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.misc.Assertions;
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
        String table0 = (table != null) ? table.stringify(dialect) : null;
        String on0 = (on != null) ? on.stringify(dialect) : null;

        if (Assertions.stringNullOrEmpty(table0)) {
            return "";
        }

        if (!Assertions.stringNullOrEmpty(on0)) {
            return Strings.format("%s %s ON %s", type.keyword, table0, on0);
        } else {
            return Strings.format("%s %s", type.keyword, table0);
        }
    }

    public static enum Type {
        NORMAL("JOIN"),
        INNER("INNER JOIN"),
        LEFT("LEFT JOIN"),
        RIGHT("RIGHT JOIN"),
        LATERAL("JOIN LATERAL"),
        LEFT_LATERAL("LEFT JOIN LATERAL"),
        RIGHT_LATERAL("RIGHT JOIN LATERAL");
        private final String keyword;

        Type(String keyword) {
            this.keyword = keyword;
        }

    }
}
