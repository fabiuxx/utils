/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.Codificables;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class CodificableEnumProjectionConverter<T extends Enum<T> & Codificable> extends AbstractCodificableProjectionConverter<T> {

    @Getter
    @Setter
    private Class<T> enumCodificableClass;

    @Override
    protected T convertCodificable(String codigo) {
        return Codificables.fromCodigo(codigo, enumCodificableClass.getEnumConstants());
    }

}
