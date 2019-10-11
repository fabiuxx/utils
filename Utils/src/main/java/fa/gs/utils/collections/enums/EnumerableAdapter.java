/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections.enums;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface EnumerableAdapter<T extends Enum<T>> {

    T getEnumerable(Object value);

}
