/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.names;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Text;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SimpleName implements Name<String> {

    private final String name;

    public SimpleName(String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return name;
    }

    @Override
    public String stringify(Dialect dialect) {
        if (Assertions.stringNullOrEmpty(name)) {
            throw new IllegalArgumentException();
        }

        if (name.equals("*")) {
            return name;
        } else {
            return Text.quoteDouble(name);
        }
    }
}
