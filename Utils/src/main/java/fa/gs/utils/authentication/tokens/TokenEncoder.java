/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.tokens;

/**
 * Clase general que permite emitir tokens JWT.
 *
 * @author Fabio A. González Sosa
 */
public interface TokenEncoder<T> {

    /**
     * Codifica un objeto en forma de cadena.
     *
     * @param payload Carga util del token.
     * @return Cadena de token.
     */
    public String encodeToken(T payload);

}
