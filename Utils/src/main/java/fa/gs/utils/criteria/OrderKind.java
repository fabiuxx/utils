/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria;

import java.io.Serializable;

/**
 * Enumeracion para el tipo de ordenacion a efectuar.
 *
 * @author Fabio A. González Sosa
 */
public enum OrderKind implements Serializable {
    ASCENDING("asc"),
    DESCENDING("desc");
    final String value;

    private OrderKind(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
