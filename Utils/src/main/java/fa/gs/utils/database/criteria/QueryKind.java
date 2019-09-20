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
public enum QueryKind implements Serializable {
    COUNT,
    SELECT,
    DELETE,
    INSERT,
    UPDATE
}
