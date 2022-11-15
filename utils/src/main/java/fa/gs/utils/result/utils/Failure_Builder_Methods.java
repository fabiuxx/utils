/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.utils;

import fa.gs.utils.misc.errors.Errno;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 * @param <B> Parametro de tipo para constructor.
 */
public interface Failure_Builder_Methods<B extends Failure_Builder_Methods<B>> {

    /**
     * Establece el valor para
     * {@link fa.gs.utils.result.utils.Failure#cause cause}.
     *
     * @param cause Causa de fallo.
     * @return Esta misma instancia.
     */
    public B cause(Throwable cause);

    /**
     * Establece el valor para
     * {@link fa.gs.utils.result.utils.Failure#errno errno}.
     *
     * @param errno Codigo numerico de error.
     * @return Esta misma instancia.
     */
    public B errno(Errno errno);

    /**
     * Agrega un nuevo tag a {@link fa.gs.utils.result.utils.Failure#tags tags}.
     *
     * @param name Nombre de tag.
     * @param value Valor de tag.
     * @return Esta misma instancia.
     */
    public B tag(String name, Object value);

    /**
     * Agrega una coleccion de tags a
     * {@link fa.gs.utils.result.utils.Failure#tags tags}.
     *
     * @param tags Coleccion de tags.
     * @return Esta misma instancia.
     */
    public B tags(Map<String, Object> tags);

}
