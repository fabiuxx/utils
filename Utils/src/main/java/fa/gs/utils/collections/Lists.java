package fa.gs.utils.collections;

import fa.gs.utils.misc.Assertions;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fabio A. González Sosa on 20-nov-2018.
 */
public class Lists {

    public static <T> List<T> empty() {
        return new LinkedList<>();
    }

    public static <T> T first(Collection<T> items) {
        try {
            if (Assertions.isNullOrEmpty(items)) {
                return null;
            }

            return items.iterator().next();
        } catch (Throwable thr) {
            return null;
        }
    }

}
