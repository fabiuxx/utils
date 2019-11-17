/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappers;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.mapping.Mapping;
import fa.gs.utils.misc.Reflect;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public class BooleanCharacterMapper extends AttributeMapper<Character> {

    protected BooleanCharacterMapper(String attributeName, Mapping<Character> mapping) {
        super(attributeName, mapping);
    }

    public static BooleanCharacterMapper instance(Mapping<Character> mapping) {
        return instance(mapping.symbol().getName(), mapping);
    }

    public static BooleanCharacterMapper instance(String attributeName, Mapping<Character> mapping) {
        return new BooleanCharacterMapper(attributeName, mapping);
    }

    @Override
    public void map(Object instance, Map<String, Object> resultSetElement) {
        Character value0 = Maps.get(resultSetElement, getMapping().symbol().getName(), getMapping().fallback(), getMapping().type());
        Boolean value = map(value0);
        Reflect.set(instance, getAttributeName(), value);
    }

    private Boolean map(Character character) {
        if (character == null) {
            return false;
        }

        if (Objects.equals(character, 'S') || Objects.equals(character, 's') || Objects.equals(character, '1')) {
            return true;
        }

        return false;
    }

}
