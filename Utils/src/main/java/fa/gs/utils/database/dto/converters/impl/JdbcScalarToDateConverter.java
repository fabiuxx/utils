/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

import fa.gs.utils.misc.errors.Errors;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JdbcScalarToDateConverter extends AbstractJdbcScalarConverter<Date> {

    @Override
    protected Date nullSafeConvert(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof java.sql.Date) {
            java.sql.Date sqlDate = (java.sql.Date) value;
            return new Date(sqlDate.getTime());
        }

        if (value instanceof java.sql.Time) {
            java.sql.Time sqlTime = (java.sql.Time) value;
            return new Date(sqlTime.getTime());
        }

        if (value instanceof java.sql.Timestamp) {
            java.sql.Timestamp sqlTimestamp = (java.sql.Timestamp) value;
            return new Date(sqlTimestamp.getTime());
        }

        throw Errors.illegalArgument("Tipo '%s' no soportado.", value.getClass().getCanonicalName());
    }

}
