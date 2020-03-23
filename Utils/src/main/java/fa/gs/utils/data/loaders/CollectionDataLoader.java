/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.data.loaders;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.criteria.Condition;
import fa.gs.utils.database.criteria.Pagination;
import fa.gs.utils.database.criteria.Sorting;
import fa.gs.utils.misc.Assertions;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 * @author Fabio A. González Sosa
 */
public class CollectionDataLoader<T> extends AbstractCollectionDataLoader<T> {

    private final Collection<T> items;

    public CollectionDataLoader(Collection<T> items) {
        this.items = Lists.empty();
        if (!Assertions.isNullOrEmpty(items)) {
            this.items.addAll(items);
        }
    }

    @Override
    protected Collection<T> getCollection() {
        return items;
    }

    @Override
    public Long countItems(Condition[] conditions) {
        return items.size() * 1L;
    }

    @Override
    public Collection loadItems(Pagination pagination, Condition[] conditions, Sorting[] sortings) {
        return items.stream()
                .skip(pagination.getOffset())
                .limit(pagination.getLimit())
                .collect(Collectors.toList());
    }

}
