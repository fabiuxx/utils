/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.utils;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo para valor almacenado.
 * @param <B> Parametro de tipo para constructor.
 */
public interface Value_Builder_Methods<T, B extends Value_Builder_Methods<T, B>> {

    /**
     * Indica si se acepta o no el valor {@code null} como valido.
     *
     * @param nullable {@code true} para que el valor {@code null} se considere
     * valido, caso contrario {@code false}.
     * @return Esta misma instancia.
     *
     * @deprecated Utilizar
     * {@link Value_Builder_Methods#strict(boolean) strict}.
     */
    @Deprecated
    public B nullable(boolean nullable);

    /**
     * Indica si se debe aplicar un control estricto para determinar si se
     * acepta o no el valor contenido como valido.
     *
     * @param strict {@code true} para indicar el modo estricto de control de
     * valores, caso contrario {@code false}.
     * @return Esta misma instancia.
     */
    public B strict(boolean strict);

    /**
     * Establece el valor a almacenar.
     *
     * @param value Objeto.
     * @return Esta misma instancia.
     */
    public B value(T value);

}
