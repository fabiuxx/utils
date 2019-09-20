/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public enum JoinKind implements Serializable {
    JOIN("JOIN"),
    LEFT("LEFT JOIN"),
    RIGHT("RIGHT JOIN"),
    NATURAL("NATURAL JOIN");
    private final String value;

    private JoinKind(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
