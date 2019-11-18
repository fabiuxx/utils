/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappers;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.mapping.Mapper;
import fa.gs.utils.database.mapping.Mapping;
import fa.gs.utils.misc.Reflect;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class AttributeMapper<T> implements Mapper<T> {

    private final String attributeName;

    private final Mapping<T> mapping;

    protected AttributeMapper(String attributeName, Mapping<T> mapping) {
        this.attributeName = attributeName;
        this.mapping = mapping;
    }

    @Override
    public void map(Object instance, String targetAttributeName, Map<String, Object> resultSetElement, Mapping<T> mapping) {
        Object value = Maps.get(resultSetElement, mapping.symbol().getName(), mapping.fallback(), mapping.type());
        Reflect.set(instance, targetAttributeName, value);
    }

    public void map(Object instance, Map<String, Object> resultSetElement) {
        map(instance, attributeName, resultSetElement, mapping);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getAttributeName() {
        return attributeName;
    }

    public Mapping<T> getMapping() {
        return mapping;
    }
    //</editor-fold>

}
