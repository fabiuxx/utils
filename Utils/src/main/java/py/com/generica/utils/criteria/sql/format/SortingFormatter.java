/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.format;

import java.util.Collection;
import py.com.generica.utils.adapters.impl.Adapter0;
import py.com.generica.utils.criteria.Sorting;
import py.com.generica.utils.misc.text.Joiner;
import py.com.generica.utils.misc.text.StringBuilder2;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SortingFormatter {

    private static final SortingAdapter adapter = new SortingAdapter();

    public static String toString(Sorting sorting) {
        return adapter.adapt(sorting);
    }

    public static String toString(Collection<Sorting> sortings) {
        Joiner joiner = Joiner.of(sortings)
                .separator(", ")
                .adapter(adapter);

        StringBuilder2 builder = new StringBuilder2();
        builder.append(joiner.join());
        builder.append(" ");
        return builder.toString();
    }

    private static class SortingAdapter extends Adapter0<Object, String> {

        @Override
        public String adapt(Object obj) {
            Sorting sorting = (Sorting) obj;
            StringBuilder2 builder = new StringBuilder2();
            builder.append(" %s", sorting.getExpression());
            if (sorting.getOrder() != null) {
                builder.append(" %s", sorting.getOrder().toString());
            }
            return builder.toString().trim();
        }

    }

}
