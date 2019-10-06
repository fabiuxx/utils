/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.sql.format;

import fa.gs.utils.adapters.impl.Adapter0;
import fa.gs.utils.database.criteria.Projection;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.StringBuilder2;
import java.util.Collection;

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
                // Agregar comillas para preservar el case. Fuente: https://stackoverflow.com/a/43112096.
                builder.append(" as \"%s\"", projection.getAs());
            }
            return builder.toString().trim();
        }

    }

}
