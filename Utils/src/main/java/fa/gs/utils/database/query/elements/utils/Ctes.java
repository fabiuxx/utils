/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.database.query.elements.CTE;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Ctes {

    public static CTE build(String name, String body) {
        CTE instance = new CTE();
        instance.setName(name);
        instance.setBody(body);
        return instance;
    }

}
