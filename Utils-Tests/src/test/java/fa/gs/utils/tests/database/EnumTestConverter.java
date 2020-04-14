/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.database;

import fa.gs.utils.database.dto.converters.DtoValueConverter;
import fa.gs.utils.database.dto.converters.DtoValueConverterTarget;

/**
 *
 * @author Fabio A. González Sosa
 */
public class EnumTestConverter implements DtoValueConverter<EnumTest> {

    @Override
    public DtoValueConverterTarget target() {
        return DtoValueConverterTarget.PROJECTION;
    }

    @Override
    public EnumTest convert(Object instance) {
        return EnumTest.E1;
    }

}
