/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.json;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.json.serialization.JsonPostConstruct;
import fa.gs.utils.misc.json.serialization.JsonProperty;
import fa.gs.utils.misc.json.serialization.JsonResolution;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Payload7 {

    @JsonProperty
    public Usuario usuario;

    @JsonProperty(resolution = JsonResolution.OPTIONAL)
    public Collection<Perfil> perfiles;

    public boolean postConstruct = false;

    @JsonPostConstruct
    void init() {
        /**
         * Inicializar a una lista vacia.
         */
        if (this.perfiles == null) {
            this.perfiles = Lists.empty();
        }

        this.postConstruct = true;
    }

}
