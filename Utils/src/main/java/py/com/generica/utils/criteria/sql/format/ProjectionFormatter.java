/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.sql.format;

import java.util.Collection;
import py.com.generica.utils.adapters.impl.Adapter0;
import py.com.generica.utils.criteria.Projection;
import py.com.generica.utils.misc.Assertions;
import py.com.generica.utils.misc.text.Joiner;
import py.com.generica.utils.misc.text.StringBuilder2;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ProjectionFormatter {

    private static final ProjectionAdapter adapter = new ProjectionAdapter();

    public static String toString(Projection projection) {
        return adapter.adapt(projection);
    }

    public static String toString(Collection<Projection> projection) {
        Joiner joiner = Joiner.of(projection)
                .separator(", ")
                .adapter(adapter);

        StringBuilder2 builder = new StringBuilder2();
        builder.append(joiner.join());
        builder.append(" ");
        return builder.toString();
    }

    private static final class ProjectionAdapter extends Adapter0<Object, String> {

        @Override
        public String adapt(Object obj) {
            Projection projection = (Projection) obj;
            StringBuilder2 builder = new StringBuilder2();
            builder.append("%s", projection.getProjection());
            if (!Assertions.stringNullOrEmpty(projection.getAs())) {
                builder.append(" as %s", projection.getAs());
            }
            return builder.toString().trim();
        }

    }

}
