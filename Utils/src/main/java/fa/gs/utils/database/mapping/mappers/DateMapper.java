/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappers;

import fa.gs.utils.database.mapping.Mapping;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class DateMapper extends AttributeMapper<Date> {

    public DateMapper(String attributeName, Mapping<Date> mapping) {
        super(attributeName, mapping);
    }

    public static DateMapper instance(Mapping<Date> mapping) {
        return instance(mapping.symbol().getName(), mapping);
    }

    public static DateMapper instance(String attributeName, Mapping<Date> mapping) {
        return new DateMapper(attributeName, mapping);
    }

}
