/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria.sql.format;

import fa.gs.utils.adapters.impl.Adapter0;
import fa.gs.utils.criteria.Grouping;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.StringBuilder2;
import java.util.Collection;

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
