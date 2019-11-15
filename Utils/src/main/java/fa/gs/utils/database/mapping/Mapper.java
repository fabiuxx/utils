/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping;

import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Mapper<T> {

    void map(Object instance, String targetAttributeName, Map<String, Object> resultSetElement, Mapping<T> mapping);

}
