/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappings;

import fa.gs.utils.database.mapping.Mapping;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Mappings {

    public static <T> Mapping<T> instance(String symbolName, Class<T> type) {
        return instance(symbolName, type, null);
    }

    public static <T> Mapping<T> instance(String symbolName, Class<T> type, T fallback) {
        return new MappingImpl<>(symbolName, type, fallback);
    }

}
