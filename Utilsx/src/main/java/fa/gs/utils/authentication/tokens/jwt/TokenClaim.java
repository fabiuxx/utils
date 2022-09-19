/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.tokens.jwt;

/**
 * Enumeracion que describe los diferentes "claims" o "propiedades" procesables
 * dentro de la carga util de un token JWT emitido.
 *
 * @author Fabio A. González Sosa
 */
public enum TokenClaim {
    VERSION("version"),
    DATA("data");
    private final String descripcion;

    /**
     * Constructor.
     *
     * @param descripcion Descripcion.
     */
    private TokenClaim(String descripcion) {
        this.descripcion = descripcion;
    }

    public String descripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
