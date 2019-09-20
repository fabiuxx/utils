/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Args {

    /**
     * Permite extraer un argumento concreto desde un array de argumentos.
     *
     * @param <T> Tipo al que se "castea" el argumento concreto, si hubiere.
     * @param objs Array de objetos que contiene el argumento a extraer.
     * @param position Posicion en la que se supone existe el argumento a
     * extraer.
     * @param fallback Valor por defecto a utilizar en caso de que no se pueda
     * obtener el argumento concreto.
     * @return Argumento concreto, su hubiere. Caso contrario se retorna el
     * valor de {@code fallback}.
     */
    public static <T> T argv(Object[] objs, int position, T fallback) {
        if (objs != null && objs.length > 0) {
            if (position >= 0 && position <= objs.length - 1) {
                Object obj = objs[position];
                return (obj != null) ? (T) obj : fallback;
            }
        }
        return fallback;
    }

    /**
     * Permite extraer un argumento concreto desde un array de argumentos.
     *
     * @param <T> Tipo al que se "castea" el argumento concreto, si hubiere.
     * @param objs Array de objetos que contiene el argumento a extraer.
     * @param position Posicion en la que se supone existe el argumento a
     * extraer.
     * @return Argumento concreto, su hubiere. Caso contrario se genera una
     * excepcion {@link ArrayIndexOutOfBoundsException}.
     * @throws ArrayIndexOutOfBoundsException Cuando no existe un valor distinto
     * de null en la posicion indicada.
     */
    public static <T> T argv(Object[] objs, int position) {
        if (objs != null && objs.length > 0) {
            if (position >= 0 && position <= objs.length - 1) {
                Object obj = objs[position];
                if (obj == null) {
                    throw new ArrayIndexOutOfBoundsException(0);
                }
                return (T) obj;
            }
        }
        throw new ArrayIndexOutOfBoundsException(0);
    }

}
