/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions;

import fa.gs.utils.database.query.QueryPart;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Operator extends QueryPart {

    String symbol();

    int precedence();

    int associativity();

    int cardinality();

    default boolean equals(Operator op) {
        if (Objects.equals(this.symbol(), op.symbol())) {
            if (Objects.equals(this.precedence(), op.precedence())) {
                return true;
            }
        }

        return false;
    }

    default int comparePrecedence(Operator other) {
        return -1 * Integer.compare(this.precedence(), other.precedence());
    }

}
