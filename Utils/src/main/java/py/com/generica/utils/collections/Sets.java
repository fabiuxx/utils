/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.collections;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Sets {

    public static <T> Set<T> empty() {
        return new HashSet<>();
    }

}
