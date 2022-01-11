/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.fechas;

import fa.gs.utils.misc.fechas.Fechas.Dia;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class DateInfo implements Serializable {

    private Date fecha;
    private Dia dia;
    private int nroSemana;
}
