/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.format;

import java.util.Collection;
import py.com.generica.utils.adapters.impl.Adapter0;
import py.com.generica.utils.criteria.Join;
import py.com.generica.utils.misc.text.Joiner;
import py.com.generica.utils.misc.text.StringBuilder2;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JoinFormatter {

    private static final JoinAdapter adapter = new JoinAdapter();

    public static String toString(Join join) {
        return adapter.adapt(join);
    }

    public static String toString(Collection<Join> joins) {
        Joiner joiner = Joiner.of(joins)
                .separator(" ")
                .adapter(adapter);

        StringBuilder2 builder = new StringBuilder2();
        builder.append(joiner.join());
        builder.append(" ");
        return builder.toString();
    }

    private static class JoinAdapter extends Adapter0<Object, String> {

        @Override
        public String adapt(Object obj) {
            Join join = (Join) obj;
            StringBuilder2 builder = new StringBuilder2();
            builder.append(" %s", join.getKind().toString());
            builder.append(" %s", ProjectionFormatter.toString(join.getProjection()));
            if (join.getJoinContidion() != null) {
                builder.append(" on %s ", ConditionFormatter.toString(join.getJoinContidion()));
            }
            return builder.toString().trim();
        }

    }

}
