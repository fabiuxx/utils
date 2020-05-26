/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Strings;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class Table implements QueryPart {

    private Expression value;
    private Name alias;

    public Table(Expression value, Name alias) {
        this.value = value;
        this.alias = alias;
    }

    @Override
    public String stringify(Dialect dialect) {
        String value0 = (value != null) ? value.stringify(dialect) : null;
        String alias0 = (alias != null) ? alias.stringify(dialect) : null;

        if (Assertions.stringNullOrEmpty(value0)) {
            throw Errors.illegalState();
        }

        if (!Assertions.stringNullOrEmpty(alias0)) {
            return Strings.format("%s AS %s", value0, alias0);
        } else {
            return Strings.format("%s", value0);
        }
    }

}
