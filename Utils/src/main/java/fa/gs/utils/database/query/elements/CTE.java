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
public class CTE implements QueryPart {

    private String name;
    private String body;

    public CTE() {
        this.name = "";
        this.body = "";
    }

    @Override
    public String stringify(Dialect dialect) {
        if (!Assertions.stringNullOrEmpty(name) && !Assertions.stringNullOrEmpty(body)) {
            return Strings.format(" %s as (%s) ", name, body);
        } else {
            return "";
        }
    }

}
