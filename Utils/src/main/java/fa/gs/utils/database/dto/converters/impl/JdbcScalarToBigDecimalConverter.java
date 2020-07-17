/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.misc.numeric.Numeric;
import java.math.BigDecimal;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JdbcScalarToBigDecimalConverter extends AbstractJdbcScalarConverter<BigDecimal> {

    @Override
    protected BigDecimal nullSafeConvert(Object value) {
        if (value instanceof Number) {
            return Numeric.adaptAsBigDecimal(value);
        }

        throw unsupportedType(value);
    }

}
