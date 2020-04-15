/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.dto.annotations.FgDto;
import fa.gs.utils.database.dto.annotations.FgGroupBy;
import fa.gs.utils.database.dto.annotations.FgHaving;
import fa.gs.utils.database.dto.annotations.FgJoin;
import fa.gs.utils.database.dto.annotations.FgOrderBy;
import fa.gs.utils.database.dto.annotations.FgProjection;
import fa.gs.utils.database.dto.annotations.FgWhere;
import fa.gs.utils.database.query.commands.CountQuery;
import fa.gs.utils.database.query.commands.SelectQuery;
import fa.gs.utils.database.query.expressions.EmptyExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.build.ConditionsExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.JoinExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.OrderExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.ProjectionExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.TableExpressionBuilder;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Strings;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class DtoQuery implements Serializable {

    public static SelectQuery prepareSelectStatement(Class klass) {
        // Validar definicion.
        validate(klass);

        // Inicializar datos.
        PreparationContext ctx = new PreparationContext(klass);
        prepareTableExpression(ctx);
        prepareTableJoinClauses(ctx);
        prepareProjectionExpressions(ctx);
        prepareWhereClauses(ctx);
        prepareHavingClauses(ctx);
        prepareGroupClauses(ctx);
        prepareOrderClauses(ctx);
        return buildSelectQuery(ctx);
    }

    public static CountQuery prepareCountStatement(Class klass) {
        SelectQuery query = prepareSelectStatement(klass);
        return query.forCount();
    }

    static void validate(Class klass) {
        // Verificar que clase contenga anotacion DTO.
        FgDto dtoAnnotation = Reflection.getAnnotation(klass, AnnotationTypes.FGDTO);
        assertIsDtoAnnotated(dtoAnnotation, klass);

        // Verificar que DTO tenga un nombre de tabla.
        assertDtoAnnotationHasTable(dtoAnnotation, klass);

        /**
         * Si existen clausulas JOIN, verificar que DTO principal posea un
         * alias.
         */
        FgJoin[] joinAnnotations = AnnotationTypes.getAllJoins(klass);
        if (!Assertions.isNullOrEmpty(joinAnnotations)) {
            assertDtoAnnotationHasAlias(dtoAnnotation, klass);
        }
    }

    private static void prepareProjectionExpressions(PreparationContext ctx) {
        Collection<Expression> projections = Lists.empty();

        Collection<Field> declaredFields = Reflection.getAllFields(ctx.klass);
        for (Field field : declaredFields) {
            FgProjection projectionAnnotation = Reflection.getAnnotation(field, AnnotationTypes.FGPROJECTION);
            if (projectionAnnotation != null) {
                ProjectionExpressionBuilder builder = ProjectionExpressionBuilder.instance();
                // Proyeccion.
                if (projectionAnnotation.useRaw()) {
                    builder.raw(projectionAnnotation.value());
                } else {
                    builder.name(projectionAnnotation.value());
                }

                // Alias.
                String alias0 = ctx.resolveAlias(projectionAnnotation);
                builder.as(alias0);

                // Expression de proyeccion.
                Expression projection = builder.build();
                projections.add(projection);
            }
        }

        ctx.projectionExpressions = projections;
    }

    private static void prepareTableExpression(PreparationContext ctx) {
        FgDto annotation = Reflection.getAnnotation(ctx.klass, AnnotationTypes.FGDTO);
        TableExpressionBuilder builder = TableExpressionBuilder.instance();

        // Nombre de tabla.
        builder.name(annotation.table());

        // Alias.
        if (!Assertions.stringNullOrEmpty(annotation.as())) {
            builder.as(annotation.as());
        }

        ctx.tableExpression = builder.build();
    }

    private static void prepareTableJoinClauses(PreparationContext ctx) {
        Collection<Expression> expressions = Lists.empty();

        FgJoin[] joins = AnnotationTypes.getAllJoins(ctx.klass);
        if (!Assertions.isNullOrEmpty(joins)) {
            for (FgJoin join : joins) {
                JoinExpressionBuilder builder = JoinExpressionBuilder.instance();
                builder.type(join.type());
                builder.name(join.table());
                builder.as(join.as());
                builder.on().raw(join.on());

                Expression expression = builder.build();
                expressions.add(expression);
            }
        }

        ctx.joinClauses = expressions;
    }

    private static void prepareWhereClauses(PreparationContext ctx) {
        Expression whereClause;
        FgWhere[] wheres = AnnotationTypes.getAllWheres(ctx.klass);
        if (!Assertions.isNullOrEmpty(wheres)) {
            ConditionsExpressionBuilder builder = ConditionsExpressionBuilder.instance();
            builder = builder.TRUE();

            for (FgWhere where : wheres) {
                String value = where.value();
                if (!Assertions.stringNullOrEmpty(value)) {
                    builder = builder.and().lpar().raw(value).rpar();
                }
            }

            whereClause = builder.build();
        } else {
            whereClause = EmptyExpression.instance();
        }

        ctx.whereClause = whereClause;
    }

    private static void prepareHavingClauses(PreparationContext ctx) {
        Expression havingClause;
        FgHaving[] havings = AnnotationTypes.getAllHavings(ctx.klass);
        if (!Assertions.isNullOrEmpty(havings)) {
            ConditionsExpressionBuilder builder = ConditionsExpressionBuilder.instance();
            builder = builder.TRUE();

            for (FgHaving where : havings) {
                String value = where.value();
                if (!Assertions.stringNullOrEmpty(value)) {
                    builder = builder.and().lpar().raw(value).rpar();
                }
            }

            havingClause = builder.build();
        } else {
            havingClause = EmptyExpression.instance();
        }

        ctx.havingClause = havingClause;
    }

    private static void prepareGroupClauses(PreparationContext ctx) {
        Collection<Expression> expressions = Lists.empty();

        FgGroupBy[] groupBys = AnnotationTypes.getAllGroupBys(ctx.klass);
        if (!Assertions.isNullOrEmpty(groupBys)) {
            for (FgGroupBy groupBy : groupBys) {
                ConditionsExpressionBuilder builder = ConditionsExpressionBuilder.instance();
                builder.raw(groupBy.value());

                Expression expression = builder.build();
                expressions.add(expression);
            }
        }

        ctx.groupClauses = expressions;
    }

    private static void prepareOrderClauses(PreparationContext ctx) {
        Collection<Expression> expressions = Lists.empty();

        FgOrderBy[] orderBys = AnnotationTypes.getAllOrderBys(ctx.klass);
        if (!Assertions.isNullOrEmpty(orderBys)) {
            for (FgOrderBy orderBy : orderBys) {
                OrderExpressionBuilder builder = OrderExpressionBuilder.instance();
                builder.type(orderBy.type());
                builder.name(orderBy.value());

                Expression expression = builder.build();
                expressions.add(expression);
            }
        }

        ctx.orderClauses = expressions;
    }

    private static <T> SelectQuery buildSelectQuery(PreparationContext<T> ctx) {
        SelectQuery query = new SelectQuery();

        // Expresion de tabla principal.
        query.from().wrap(ctx.tableExpression);

        // Clausulas join.
        ctx.joinClauses.forEach(exp -> query.join().wrap(exp));

        // Proyecciones.
        ctx.projectionExpressions.forEach(exp -> query.projection().wrap(exp));

        // Clausula where.
        query.where().wrap(ctx.whereClause);

        // Clausulas de agrupacion.
        ctx.groupClauses.forEach((groupBy) -> query.group().wrap(groupBy));

        // Clausula having.
        query.having().wrap(ctx.havingClause);

        // Criterios de ordenacion.
        ctx.orderClauses.forEach((orderBy) -> query.order().wrap(orderBy));

        return query;
    }

    //<editor-fold defaultstate="collapsed" desc="Controles de validacion">
    private static void assertIsDtoAnnotated(FgDto annotation, Class klass) {
        if (annotation == null) {
            throw Errors.illegalArgument("Clase '%s' no es un DTO.", klass.getCanonicalName());
        }
    }

    private static void assertDtoAnnotationHasTable(FgDto annotation, Class klass) {
        if (Assertions.stringNullOrEmpty(annotation.table())) {
            throw Errors.illegalArgument("Clase '%s' no posee un nombre de tabla.", klass.getCanonicalName());
        }
    }

    private static void assertDtoAnnotationHasAlias(FgDto annotation, Class klass) {
        if (Assertions.stringNullOrEmpty(annotation.as())) {
            throw Errors.illegalArgument("Clase '%s' no posee un alias.", klass.getCanonicalName());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Clases Auxiliares">
    private static class PreparationContext<T> {

        private Long counter;
        private Class<T> klass;
        private Expression tableExpression;
        private Collection<Expression> joinClauses;
        private Collection<Expression> projectionExpressions;
        private Expression whereClause;
        private Expression havingClause;
        private Collection<Expression> groupClauses;
        private Collection<Expression> orderClauses;

        public PreparationContext(Class<T> klass) {
            this.klass = klass;
            this.counter = 0L;
        }

        public Long nextCount() {
            return counter++;
        }

        public String resolveAlias(FgProjection projectionAnnotation) {
            if (Assertions.stringNullOrEmpty(projectionAnnotation.as())) {
                return Strings.format("p%d", nextCount());
            } else {
                return projectionAnnotation.as();
            }
        }

    }
    //</editor-fold>
}
