/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class DtoProjectionConverter<T> implements DtoValueConverter<T> {

    @Override
    public DtoValueConverterTarget target() {
        return DtoValueConverterTarget.PROJECTION;
    }

    @Override
    public T convert(Object instance) {
        return convertProjection(instance);
    }

    public abstract T convertProjection(Object projection);

}
