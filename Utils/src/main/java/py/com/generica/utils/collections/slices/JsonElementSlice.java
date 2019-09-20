package py.com.generica.utils.collections.slices;

import com.google.gson.JsonElement;
import java.util.Collection;

/**
 * Created by Fabio A. González Sosa on 20-nov-2018.
 */
public class JsonElementSlice extends CollectionSlice<JsonElement> {

    /**
     * Constructor.
     *
     * @param elements Elementos.
     * @param subtotal Subtotal de elementos.
     * @param total Total general de elementos.
     */
    JsonElementSlice(Collection<JsonElement> elements, int subtotal, int total) {
        super(elements, subtotal, total);
    }

}
