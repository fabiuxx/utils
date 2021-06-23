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
import java8.util.function.Supplier;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Expressions {

    public static Expression TRUE() {
        ExpressionBuilder builder = ExpressionBuilder.instance();
        builder.TRUE();
        return builder.build();
    }

    public static Expression FALSE() {
        ExpressionBuilder builder = ExpressionBuilder.instance();
        builder.FALSE();
        return builder.build();
    }

    /**
     * @param expressions Expresiones a unificar en una sola.
     * @return Expresion que contiene a todas las expresiones individuales,
     * unidas por operadores 'AND'.
     * @deprecated Utilizar las funciones mas especificas
     * {@link Expressions#joinWithAnd(java.util.Collection) joinWithAnd} o
     * {@link Expressions#joinWithOr(java.util.Collection) joinWithOr}.
     */
    @Deprecated
    public static Expression join(Collection<Expression> expressions) {
        return joinWithAnd(expressions);
    }

    public static Expression joinWithAnd(Expression... expressions) {
        Collection<Expression> expressions0 = Lists.empty();
        Lists.add(expressions0, expressions);
        return joinWithAnd(expressions0);
    }

    public static Expression joinWithAnd(Collection<Expression> expressions) {
        return joinWithAnd(expressions, () -> Expressions.TRUE());
    }

    public static Expression joinWithAnd(Collection<Expression> expressions, Supplier<Expression> fallback) {
        // Control de seguridad.
        if (Assertions.isNullOrEmpty(expressions)) {
            return fallback.get();
        }

        ExpressionBuilder builder = ExpressionBuilder.instance();
        builder.TRUE();
        for (Expression expression : expressions) {
            builder.and().lpar().wrap(expression).rpar();
        }
        return builder.build();
    }

    public static Expression joinWithOr(Expression... expressions) {
        Collection<Expression> expressions0 = Lists.empty();
        Lists.add(expressions0, expressions);
        return joinWithOr(expressions0);
    }

    public static Expression joinWithOr(Collection<Expression> expressions) {
        return joinWithOr(expressions, () -> Expressions.TRUE());
    }

    public static Expression joinWithOr(Collection<Expression> expressions, Supplier<Expression> fallback) {
        // Control de seguridad.
        if (Assertions.isNullOrEmpty(expressions)) {
            return fallback.get();
        }

        ExpressionBuilder builder = ExpressionBuilder.instance();
        builder.FALSE();
        for (Expression expression : expressions) {
            builder.or().lpar().wrap(expression).rpar();
        }
        return builder.build();
    }

    public static Expression build(QueryPart part) {
        ExpressionImpl exp = new ExpressionImpl();
        exp.add(part);
        return exp;
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
