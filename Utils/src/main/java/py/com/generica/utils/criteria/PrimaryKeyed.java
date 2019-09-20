/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public interface PrimaryKeyed<T> extends Serializable {

    public T getPK();

    public Class<T> getPKType();

}
