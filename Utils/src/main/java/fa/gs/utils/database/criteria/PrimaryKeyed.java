/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public interface PrimaryKeyed<T> extends Serializable {

    public T getPK();

    public Class<T> getPKType();

}
