/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions;

import fa.gs.utils.database.query.Dialect;

/**
 *
 * @author Fabio A. González Sosa
 */
public class EmptyExpression implements Expression {

    public static Expression instance() {
        return new EmptyExpression();
    }

    @Override
    public String stringify(Dialect dialect) {
        return "";
    }

}
