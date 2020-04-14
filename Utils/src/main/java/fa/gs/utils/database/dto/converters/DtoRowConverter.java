/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters;

import fa.gs.utils.collections.Maps;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class DtoRowConverter<T> implements DtoValueConverter<T> {

    @Override
    public DtoValueConverterTarget target() {
        return DtoValueConverterTarget.ROW;
    }

    @Override
    public T convert(Object instance) {
        Map<String, Object> row = Maps.empty();
        row.putAll((Map<String, Object>) instance);
        return convertRow(row);
    }

    public abstract T convertRow(Map<String, Object> row);

}
