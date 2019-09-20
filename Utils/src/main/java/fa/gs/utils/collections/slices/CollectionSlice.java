package fa.gs.utils.collections.slices;

import java.util.Collection;

/**
 * Created by Fabio A. González Sosa on 20-nov-2018.
 */
public class CollectionSlice<T> extends AbstractSlice<Collection<T>> {

    /**
     * Constructor.
     *
     * @param elements Elementos.
     * @param subtotal Subtotal de elementos.
     * @param total Total general de elementos.
     */
    CollectionSlice(Collection<T> elements, int subtotal, int total) {
        super(elements, subtotal, total);
    }

}
