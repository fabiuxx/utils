/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria.sql.format;

import fa.gs.utils.adapters.impl.Adapter0;
import fa.gs.utils.criteria.Condition;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.StringBuilder2;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ConditionFormatter {

    private static final ConditionAdapter adapter = new ConditionAdapter();

    public static String toString(Condition condition) {
        return adapter.adapt(condition);
    }

    public static String toString(Collection<Condition> conditions) {
        Joiner joiner = Joiner.of(conditions)
                .separator(" and ")
                .adapter(adapter);

        StringBuilder2 builder = new StringBuilder2();
        builder.append(joiner.join());
        builder.append(" ");
        return builder.toString();
    }

    private static class ConditionAdapter extends Adapter0<Object, String> {

        @Override
        public String adapt(Object obj) {
            Condition condition = (Condition) obj;
            StringBuilder2 builder = new StringBuilder2();
            builder.append("(");
            builder.append(" %s", condition.getLeftExpression());
            builder.append(" %s", condition.getOperator().symbol());
            builder.append(" %s", String.valueOf(condition.getRightExpression()));
            builder.append(" )");
            return builder.toString().trim();
        }

    }

}
