/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.models.table;

import fa.gs.utils.database.query.expressions.Expression;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public interface TableModel<T> {

    List<T> getSelectedItems();

    Collection<TableModelItem<T>> loadItems(long limit, long offset, Expression filterBy, Expression sortBy);

}
