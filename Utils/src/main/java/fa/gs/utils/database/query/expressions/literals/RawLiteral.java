/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.literals;

import fa.gs.utils.database.query.Dialect;

/**
 *
 * @author Fabio A. González Sosa
 */
public class RawLiteral implements Literal<String> {

    private final String value;

    public RawLiteral(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String stringify(Dialect dialect) {
        return value;
    }

}
