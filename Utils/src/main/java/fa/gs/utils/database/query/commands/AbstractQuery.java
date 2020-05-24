/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.commands;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.misc.Assertions;
import java.util.Arrays;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractQuery implements QueryPart {

    protected String[] stringify(QueryPart[] parts, Dialect dialect) {
        if (Assertions.isNullOrEmpty(parts)) {
            return null;
        } else {
            return Arrays.stream(parts)
                    .filter(e -> e != null)
                    .map(e -> e.stringify(dialect))
                    .toArray(String[]::new);
        }
    }

}
