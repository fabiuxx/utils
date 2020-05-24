/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.literals;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.misc.errors.Errors;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class DateLiteral implements Literal<Date> {

    private final Date value;
    private final DateType type;

    public DateLiteral(Date value, DateType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Date value() {
        return value;
    }

    public DateType dateType() {
        return type;
    }

    @Override
    public String stringify(Dialect dialect) {
        switch (type) {
            case FECHA:
                return SQLStringLiterals.fecha(value);
            case HORA:
                return SQLStringLiterals.hora(value);
            case FECHA_HORA:
                return SQLStringLiterals.fechaHora(value);
            default:
                throw Errors.illegalState();
        }
    }

    public static enum DateType {
        FECHA,
        HORA,
        FECHA_HORA
    }

}
