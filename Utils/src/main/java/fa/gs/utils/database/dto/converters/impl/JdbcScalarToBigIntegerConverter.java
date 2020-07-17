/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.misc.numeric.Numeric;
import java.math.BigInteger;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JdbcScalarToBigIntegerConverter extends AbstractJdbcScalarConverter<BigInteger> {

    @Override
    public BigInteger nullSafeConvert(Object value) {
        if (value instanceof Number) {
            return Numeric.adaptAsBigInteger(value);
        }

        throw unsupportedType(value);
    }

}
