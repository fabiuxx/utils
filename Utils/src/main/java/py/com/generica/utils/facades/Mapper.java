/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.facades;

import java.io.Serializable;
import java.util.Map;
import py.com.generica.utils.adapters.impl.Adapter0;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public abstract class Mapper<T> extends Adapter0<Map<String, Object>, T> implements Serializable {

}
