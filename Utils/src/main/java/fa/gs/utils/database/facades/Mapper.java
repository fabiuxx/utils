/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.facades;

import fa.gs.utils.adapters.impl.Adapter0;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class Mapper<T> extends Adapter0<Map<String, Object>, T> implements Serializable {

}
