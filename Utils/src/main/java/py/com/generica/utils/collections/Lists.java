package py.com.generica.utils.collections;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fabio A. González Sosa on 20-nov-2018.
 */
public class Lists {

    public static <T> List<T> empty() {
        return new LinkedList<>();
    }

}
