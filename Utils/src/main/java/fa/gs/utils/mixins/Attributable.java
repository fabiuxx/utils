/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.mixins;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Attributable extends Serializable {

    public boolean has(String name);

    public void set(String name, Object value);

    default public void set(Map<String, Object> attributes) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    default public <V> V get(String name) {
        return get(name, null);
    }

    public <V> V get(String name, V fallback);

}
