/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.mixins;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Attributable extends Serializable {

    public boolean has(String name);

    public void set(String name, Object value);

    public <V> V get(String name, V fallback);

}
