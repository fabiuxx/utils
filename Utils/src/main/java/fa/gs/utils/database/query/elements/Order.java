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
public class Order implements QueryPart {

    private Name value;
    private Order.Type type;

    public Order(Name value, Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String stringify(Dialect dialect) {
        if (type != null) {
            return Strings.format("%s %s", value.stringify(dialect), type.keyword);
        } else {
            return value.stringify(dialect);
        }
    }

    public static enum Type {
        ASC("ASC"),
        DESC("DESC");
        private final String keyword;

        Type(String keyword) {
            this.keyword = keyword;
        }
    }
}
