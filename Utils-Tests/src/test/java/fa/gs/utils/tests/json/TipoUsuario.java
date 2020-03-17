/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.json;

import fa.gs.utils.misc.errors.Errors;
import java.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public enum TipoUsuario {
    TIPO1("0001"),
    TIPO2("0002");
    private final String codigo;

    private TipoUsuario(String value) {
        this.codigo = value;
    }

    public static TipoUsuario fromCodigo(String codigo) {
        for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
            if (Objects.equals(tipoUsuario.codigo, codigo)) {
                return tipoUsuario;
            }
        }

        throw Errors.illegalArgument("Codigo '%s' desconocido.", codigo);
    }
}
