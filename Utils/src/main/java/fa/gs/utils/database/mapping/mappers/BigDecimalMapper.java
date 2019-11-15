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
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class BigDecimalMapper extends AttributeMapper<BigDecimal> {

    public BigDecimalMapper(String attributeName, Mapping<BigDecimal> mapping) {
        super(attributeName, mapping);
    }

    @Override
    public void map(Object instance, String targetAttributeName, Map<String, Object> resultSetElement, Mapping<BigDecimal> mapping) {
        Object value0 = Maps.get(resultSetElement, mapping.symbol().getName(), mapping.fallback(), mapping.type());
        BigDecimal value = Numeric.adaptAsBigDecimal(value0);
        Reflect.set(instance, targetAttributeName, value);
    }

}
