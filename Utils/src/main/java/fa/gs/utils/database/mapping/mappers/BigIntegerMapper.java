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
import java.math.BigInteger;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class BigIntegerMapper extends AttributeMapper<BigInteger> {

    public BigIntegerMapper(String attributeName, Mapping<BigInteger> mapping) {
        super(attributeName, mapping);
    }

    @Override
    public void map(Object instance, String targetAttributeName, Map<String, Object> resultSetElement, Mapping<BigInteger> mapping) {
        Object value0 = Maps.get(resultSetElement, mapping.symbol().getName(), mapping.fallback(), mapping.type());
        BigInteger value = Numeric.adaptAsBigInteger(value0);
        Reflect.set(instance, targetAttributeName, value);
    }

}
