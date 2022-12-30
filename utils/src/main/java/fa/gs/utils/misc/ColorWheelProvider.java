/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface ColorWheelProvider {

    /**
     * Dado un angulo entre 0 y 360, calcula el color correspondiente asumiendo
     * una rueda del espectro RGB.
     *
     * @param angle Angulo de rotacion.
     * @return Componentes r,g,b de color asociado.
     */
    int[] getColor(double angle);

}
