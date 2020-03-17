/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.json;

import fa.gs.utils.misc.json.serialization.JsonProperty;
import fa.gs.utils.misc.json.serialization.JsonResolution;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Payload3 {

    @JsonProperty
    public Usuario usuario;

    @JsonProperty(resolution = JsonResolution.OPTIONAL)
    public Perfil perfil;

}
