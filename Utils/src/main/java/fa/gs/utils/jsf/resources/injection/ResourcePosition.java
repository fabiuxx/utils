/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.injection;

import fa.gs.utils.misc.Assertions;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public enum ResourcePosition {
    ANY("any"),
    CORE("core"),
    FIRST("first"),
    MIDDLE("middle"),
    LAST("last");
    private final String value;

    private ResourcePosition(String value) {
        this.value = value;
    }

    public static ResourcePosition from(String value) {
        if (Assertions.stringNullOrEmpty(value)) {
            return ANY;
        }

        for (ResourcePosition position : values()) {
            if (Objects.equals(position.position(), value)) {
                return position;
            }
        }
        return ANY;
    }

    public String position() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
