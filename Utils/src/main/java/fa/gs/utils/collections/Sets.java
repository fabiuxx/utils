/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Sets {

    public static <T> Set<T> empty() {
        return new LinkedHashSet<>();
    }

}
