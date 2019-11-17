/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappers;

import fa.gs.utils.database.mapping.Mapping;

/**
 *
 * @author Fabio A. González Sosa
 */
public class StringMapper extends AttributeMapper<String> {

    public StringMapper(String attributeName, Mapping<String> mapping) {
        super(attributeName, mapping);
    }

    public static StringMapper instance(Mapping<String> mapping) {
        return instance(mapping.symbol().getName(), mapping);
    }

    public static StringMapper instance(String attributeName, Mapping<String> mapping) {
        return new StringMapper(attributeName, mapping);
    }

}
