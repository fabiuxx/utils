/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.injection;

import fa.gs.utils.misc.text.Text;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ResourceRequirement {

    public static final Integer MIN_PRIORITY = 100;

    public static final Integer MAX_PRIORITY = 0;

    private final String path;
    private final String library;
    private final Integer priority;

    public ResourceRequirement(String path, String library) {
        this(path, library, Integer.MAX_VALUE);
    }

    public ResourceRequirement(String path, String library, Integer priority) {
        this.path = Text.normalizeSlashes(path);
        this.library = library;
        this.priority = Math.max(priority, MAX_PRIORITY);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getPath() {
        return path;
    }

    public String getLibrary() {
        return library;
    }

    public Integer getPriority() {
        return priority;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.path);
        hash = 29 * hash + Objects.hashCode(this.library);
        hash = 29 * hash + Objects.hashCode(this.priority);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResourceRequirement other = (ResourceRequirement) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        if (!Objects.equals(this.library, other.library)) {
            return false;
        }
        if (!Objects.equals(this.priority, other.priority)) {
            return false;
        }
        return true;
    }

}
