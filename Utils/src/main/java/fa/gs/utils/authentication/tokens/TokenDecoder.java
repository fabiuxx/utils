/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.authentication.tokens;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface TokenDecoder<T> {

    /**
     * Decodifica un token y obtiene la carga util que representa la misma.
     *
     * @param token Token en forma de cadena.
     * @return Carga util del token.
     */
    public T decodeToken(String token);

}
