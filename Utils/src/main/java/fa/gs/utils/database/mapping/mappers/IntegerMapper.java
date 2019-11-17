/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappers;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.mapping.Mapping;
import fa.gs.utils.misc.Numeric;
import fa.gs.utils.misc.Reflect;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class IntegerMapper extends AttributeMapper<Integer> {

    protected IntegerMapper(String attributeName, Mapping<Integer> mapping) {
        super(attributeName, mapping);
    }

    public static IntegerMapper instance(Mapping<Integer> mapping) {
        return instance(mapping.symbol().getName(), mapping);
    }

    public static IntegerMapper instance(String attributeName, Mapping<Integer> mapping) {
        return new IntegerMapper(attributeName, mapping);
    }

    @Override
    public void map(Object instance, String targetAttributeName, Map<String, Object> resultSetElement, Mapping<Integer> mapping) {
        Object value0 = Maps.get(resultSetElement, mapping.symbol().getName(), mapping.fallback(), Object.class);
        Integer value = Numeric.adaptAsInteger(value0);
        Reflect.set(instance, targetAttributeName, value);
    }

}
