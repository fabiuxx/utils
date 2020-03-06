/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.database;

import fa.gs.utils.database.dto.FgProjectionResultConverter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class EnumTestConverter implements FgProjectionResultConverter<EnumTest> {

    @Override
    public EnumTest convert(Object instance) {
        return EnumTest.E1;
    }

}
