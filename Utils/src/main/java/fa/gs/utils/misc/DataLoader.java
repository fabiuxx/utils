/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.criteria.Condition;
import fa.gs.utils.criteria.Pagination;
import fa.gs.utils.criteria.Sorting;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public interface DataLoader<T> extends Serializable {

    Long countItems(Collection<Condition> conditions);

    Collection<T> loadItems(Pagination pagination, Collection<Condition> conditions, Collection<Sorting> sortings);

}
