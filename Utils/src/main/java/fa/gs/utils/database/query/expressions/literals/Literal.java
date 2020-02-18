/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.literals;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Literal<T> extends QueryPart {

    T value();

    @Override
    default String stringify(Dialect dialect) {
        return String.valueOf(value());
    }

}
