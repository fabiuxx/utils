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

    private final String path;
    private final String library;
    private final ResourcePosition position;

    public ResourceRequirement(String path, String library) {
        this(path, library, ResourcePosition.ANY);
    }

    public ResourceRequirement(String path, String library, ResourcePosition position) {
        this.path = Text.normalizeSlashes(path);
        this.library = library;
        this.position = position;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getPath() {
        return path;
    }

    public String getLibrary() {
        return library;
    }

    public ResourcePosition getPosition() {
        return position;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.path);
        hash = 29 * hash + Objects.hashCode(this.library);
        hash = 29 * hash + Objects.hashCode(this.position);
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
        if (this.position != other.position) {
            return false;
        }
        return true;
    }

}
