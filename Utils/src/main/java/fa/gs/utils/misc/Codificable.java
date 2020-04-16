/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import java.io.Serializable;

/**
 *
 * @author Sergio D. Riveros Vazquez
 */
public interface Codificable extends Serializable {

    String codigo();

    String descripcion();

}
