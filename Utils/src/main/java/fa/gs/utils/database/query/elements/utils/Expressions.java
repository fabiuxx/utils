/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.utils;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.build.ExpressionBuilder;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.StringBuilder2;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Expressions {

    public static Expression join(Collection<Expression> expressions) {
        ExpressionBuilder builder = ExpressionBuilder.instance();
        builder.TRUE();
        for (Expression expression : expressions) {
            builder.and().lpar().wrap(expression).rpar();
        }
        return builder.build();
    }

    public static Expression build(Collection<QueryPart> parts) {
        ExpressionImpl exp = new ExpressionImpl();
        exp.add(parts);
        return exp;
    }

    public static Expression build(final String raw) {
        ExpressionImpl exp = new ExpressionImpl();
        exp.add(raw);
        return exp;
    }

    private static class ExpressionImpl implements Expression {

        private final Collection<QueryPart> parts;

        private ExpressionImpl() {
            this.parts = Lists.empty();
        }

        private void add(final String raw) {
            add(new QueryPart() {
                @Override
                public String stringify(Dialect dialect) {
                    return raw;
                }
            });
        }

        private void add(QueryPart part0) {
            if (part0 != null) {
                parts.add(part0);
            }
        }

        private void add(Collection<QueryPart> parts0) {
            if (!Assertions.isNullOrEmpty(parts0)) {
                parts.addAll(parts0);
            }
        }

        @Override
        public String stringify(Dialect dialect) {
            final StringBuilder2 builder = new StringBuilder2();
            for (QueryPart part : parts) {
                String exp0 = part.stringify(dialect);
                builder.append(" %s ", exp0);
            }

            return builder.toString();
        }

    }

}
