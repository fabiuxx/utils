/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.data.loaders;

import fa.gs.utils.database.criteria.Condition;
import fa.gs.utils.database.criteria.Pagination;
import fa.gs.utils.database.criteria.Sorting;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public abstract class AbstractCollectionDataLoader<T> implements DataLoader<T> {

    protected abstract Collection<T> getCollection();

    @Override
    public Long countItems(Condition[] conditions) {
        return getCollection().size() * 1L;
    }

    @Override
    public Collection loadItems(Pagination pagination, Condition[] conditions, Sorting[] sortings) {
        return getCollection().stream()
                .skip(pagination.getOffset())
                .limit(pagination.getLimit())
                .collect(Collectors.toList());
    }
}
