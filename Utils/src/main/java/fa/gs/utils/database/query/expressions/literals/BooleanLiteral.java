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
public class BooleanLiteral implements Literal<Boolean> {

    private final Boolean value;

    public BooleanLiteral(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean value() {
        return value;
    }

}
