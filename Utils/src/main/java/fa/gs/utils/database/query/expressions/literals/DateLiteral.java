/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.literals;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.text.Strings;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class DateLiteral implements Literal<Date> {

    private final Date value;

    public DateLiteral(Date value) {
        this.value = value;
    }

    @Override
    public Date value() {
        return value;
    }

    @Override
    public String stringify(Dialect dialect) {
        String txt = Fechas.toString(value, "yyyy-MM-dd hh:mm:ss");
        return Strings.format("'%s'", txt);
    }

}
