/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.models.selection;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface SelectionModelItem<T> {

    String getItemKey();

    String getLabel();

    T getValue();

}
