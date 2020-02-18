/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.literals;

/**
 *
 * @author Fabio A. González Sosa
 */
public class NumberLiteral implements Literal<Number> {

    private final Number value;

    public NumberLiteral(Number value) {
        this.value = value;
    }

    @Override
    public Number value() {
        return value;
    }

}
