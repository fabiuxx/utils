/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.format;

import java.util.Collection;
import py.com.generica.utils.adapters.impl.Adapter0;
import py.com.generica.utils.criteria.Grouping;
import py.com.generica.utils.misc.text.Joiner;
import py.com.generica.utils.misc.text.StringBuilder2;

/**
 *
 * @author Fabio A. González Sosa
 */
public class GroupingFormatter {

    private static final GroupingAdapter adapter = new GroupingAdapter();

    public static String toString(Grouping grouping) {
        return adapter.adapt(grouping);
    }

    public static String toString(Collection<Grouping> groupings) {
        Joiner joiner = Joiner.of(groupings)
                .separator(", ")
                .adapter(adapter);

        StringBuilder2 builder = new StringBuilder2();
        builder.append(joiner.join());
        builder.append(" ");
        return builder.toString();
    }

    private static class GroupingAdapter extends Adapter0<Object, String> {

        @Override
        public String adapt(Object obj) {
            Grouping grouping = (Grouping) obj;
            StringBuilder2 builder = new StringBuilder2();
            builder.append(" %s", grouping.getExpression());
            return builder.toString().trim();
        }

    }

}
