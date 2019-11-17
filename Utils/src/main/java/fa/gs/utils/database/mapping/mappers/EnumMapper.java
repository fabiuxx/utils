/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappers;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.collections.enums.EnumerableAdapter;
import fa.gs.utils.database.mapping.Mapping;
import fa.gs.utils.misc.Reflect;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 * @param <U>
 */
public class EnumMapper<T, U extends Enum<U>> extends AttributeMapper<T> {

    private final EnumerableAdapter<U> adapter;

    protected EnumMapper(String attributeName, Mapping<T> mapping, EnumerableAdapter<U> adapter) {
        super(attributeName, mapping);
        this.adapter = adapter;
    }

    public static <T, U extends Enum<U>> EnumMapper instance(Mapping<T> mapping, EnumerableAdapter<U> adapter) {
        return instance(mapping.symbol().getName(), mapping, adapter);
    }

    public static <T, U extends Enum<U>> EnumMapper instance(String attributeName, Mapping<T> mapping, EnumerableAdapter<U> adapter) {
        return new EnumMapper(attributeName, mapping, adapter);
    }

    @Override
    public void map(Object instance, Map<String, Object> resultSetElement) {
        Object value = Maps.get(resultSetElement, getMapping().symbol().getName(), getMapping().fallback(), getMapping().type());
        U enumValue = adapter.getEnumerable(value);
        Reflect.set(instance, getAttributeName(), enumValue);
    }

}
