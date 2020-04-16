/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.collections.Enums;
import fa.gs.utils.misc.Codificable;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class CodificableEnumProjectionConverter<T extends Enum<T> & Codificable> extends AbstractCodificableProjectionConverter<T> {

    private final Class<T> enumClass;

    protected CodificableEnumProjectionConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    protected T convertCodificable(String codigo) {
        return Enums.getCodificable(codigo, enumClass);
    }

}
