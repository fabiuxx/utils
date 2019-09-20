package fa.gs.utils.collections.slices;

import com.google.gson.JsonElement;
import fa.gs.utils.collections.Lists;
import java.util.Collection;

/**
 * Created by Fabio A. González Sosa on 20-nov-2018.
 */
public class Slices {

    public static <T> CollectionSlice<T> collection() {
        return collection(Lists.<T>empty(), 0, 0);
    }

    public static <T> CollectionSlice<T> collection(Collection<T> elements) {
        return collection(elements, elements.size(), elements.size());
    }

    public static <T> CollectionSlice<T> collection(Collection<T> elements, int subtotal, int total) {
        return new CollectionSlice<>(elements, subtotal, total);
    }

    public static JsonElementSlice jsonElements() {
        return jsonElements(Lists.<JsonElement>empty(), 0, 0);
    }

    public static JsonElementSlice jsonElements(Collection<JsonElement> elements) {
        return jsonElements(elements, elements.size(), elements.size());
    }

    public static JsonElementSlice jsonElements(Collection<JsonElement> elements, int subtotal, int total) {
        return new JsonElementSlice(elements, subtotal, total);
    }

}
