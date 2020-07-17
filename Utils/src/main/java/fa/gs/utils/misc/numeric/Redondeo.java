/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.numeric;

import java.math.RoundingMode;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Redondeo {

    public static final RoundingMode TRUNCAR = RoundingMode.FLOOR;

    public static final RoundingMode HACIA_ARRIBA = RoundingMode.UP;

    public static final RoundingMode HACIA_ABAJO = RoundingMode.DOWN;

    public static final RoundingMode MITAD_AL_PAR = RoundingMode.HALF_EVEN;

    public static final RoundingMode MITAD_HACIA_ARRIBA = RoundingMode.HALF_UP;

    public static final RoundingMode MITAD_HACIA_ABAJO = RoundingMode.HALF_DOWN;

}
