/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc;

import java.io.Serializable;
import java.util.Collection;
import py.com.generica.utils.criteria.Condition;
import py.com.generica.utils.criteria.Pagination;
import py.com.generica.utils.criteria.Sorting;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public interface DataLoader<T> extends Serializable {

    Long countItems(Collection<Condition> conditions);

    Collection<T> loadItems(Pagination pagination, Collection<Condition> conditions, Collection<Sorting> sortings);

}
