/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.dto.annotations.FgCte;
import fa.gs.utils.database.dto.annotations.FgDto;
import fa.gs.utils.database.dto.annotations.FgGroupBy;
import fa.gs.utils.database.dto.annotations.FgHaving;
import fa.gs.utils.database.dto.annotations.FgJoin;
import fa.gs.utils.database.dto.annotations.FgOrderBy;
import fa.gs.utils.database.dto.annotations.FgProjection;
import fa.gs.utils.database.dto.annotations.FgWhere;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.database.query.commands.CountQuery;
import fa.gs.utils.database.query.commands.SelectQuery;
import fa.gs.utils.database.query.elements.CTE;
import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.Join;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Order;
import fa.gs.utils.database.query.elements.Projection;
import fa.gs.utils.database.query.elements.Table;
import fa.gs.utils.database.query.elements.build.ExpressionBuilder;
import fa.gs.utils.database.query.elements.utils.Ctes;
import fa.gs.utils.database.query.elements.utils.Joins;
import fa.gs.utils.database.query.elements.utils.Projections;
import fa.gs.utils.database.query.elements.utils.Tables;
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
        prepareCommonTableExpressions(ctx);
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
        return query.asCountQuery();
    }

    private static void validate(Class klass) {
        // Verificar que clase contenga anotacion DTO.
        FgDto dtoAnnotation = Reflection.getAnnotation(klass, AnnotationTypes.FGDTO);
        if (dtoAnnotation == null) {
            throw Errors.illegalArgument("Clase '%s' no es un DTO.", klass.getCanonicalName());
        }

        // Verificar que DTO tenga un nombre de tabla.
        if (Assertions.stringNullOrEmpty(dtoAnnotation.table())) {
            throw Errors.illegalArgument("Clase '%s' no posee un nombre de tabla.", klass.getCanonicalName());
        }

        /**
         * Si existen clausulas JOIN, verificar que DTO principal posea un
         * alias.
         */
        FgJoin[] joinAnnotations = AnnotationTypes.getAllJoins(klass);
        if (!Assertions.isNullOrEmpty(joinAnnotations) && Assertions.stringNullOrEmpty(dtoAnnotation.as())) {
            throw Errors.illegalArgument("Clase '%s' no posee un alias.", klass.getCanonicalName());
        }

        /**
         * Verificar que existan definiciones de proyeccion.
         */
        Collection<Field> declaredFields = Reflection.getAllFields(klass);
        if (Assertions.isNullOrEmpty(declaredFields)) {
            throw Errors.illegalArgument("Clase '%s' no posee proyecciones.", klass.getCanonicalName());
        }

        /**
         * Si existen clausulas de proyeccion, verificar que las mismas tengan
         * un "valor" de proyeccion.
         */
        for (Field field : declaredFields) {
            FgProjection projectionAnnotation = Reflection.getAnnotation(field, AnnotationTypes.FGPROJECTION);
            if (projectionAnnotation != null && Assertions.stringNullOrEmpty(projectionAnnotation.value())) {
                throw Errors.illegalArgument("El campo '%s' no define ninguna proyeccion especifica.", field.getName());
            }
        }
    }

    private static void prepareProjectionExpressions(PreparationContext ctx) {
        Collection<Projection> projections = Lists.empty();

        Collection<Field> declaredFields = Reflection.getAllFields(ctx.klass);
        for (Field field : declaredFields) {
            FgProjection projectionAnnotation = Reflection.getAnnotation(field, AnnotationTypes.FGPROJECTION);
            if (projectionAnnotation != null) {
                // 1) Resolver proyeccion.
                Object projection0 = ctx.resolveProjection(field, projectionAnnotation);

                // 2) Resolver alias.
                String alias0 = ctx.resolveAlias(field, projectionAnnotation);

                // 3) Expression de proyeccion.
                Projection projection = Projections.build(projection0, alias0);
                projections.add(projection);
            }
        }

        ctx.projections = projections;
    }

    private static void prepareCommonTableExpressions(PreparationContext ctx) {
        Collection<QueryPart> ctes = Lists.empty();

        FgCte[] ctes0 = AnnotationTypes.getAllCtes(ctx.klass);
        if (!Assertions.isNullOrEmpty(ctes0)) {
            for (FgCte cte0 : ctes0) {
                // 1) Resolver nombre.
                String name = cte0.name();

                // 2) Resolver cuerpo.
                String body = cte0.body();

                // 3) CTE.
                CTE cte = Ctes.build(name, body);
                ctes.add(cte);
            }
        }
        
        ctx.ctes = ctes;
    }

    private static void prepareTableExpression(PreparationContext ctx) {
        FgDto annotation = Reflection.getAnnotation(ctx.klass, AnnotationTypes.FGDTO);

        // 1) Resolver nombre.
        Object name0 = new Name(annotation.table());

        // 2) Resolver alias.
        String alias0 = annotation.as();

        // 3) Expresion de tabla.
        Table table = Tables.build(name0, alias0);
        ctx.table = table;
    }

    private static void prepareTableJoinClauses(PreparationContext ctx) {
        Collection<Join> expressions = Lists.empty();

        FgJoin[] joins = AnnotationTypes.getAllJoins(ctx.klass);
        if (!Assertions.isNullOrEmpty(joins)) {
            for (FgJoin join : joins) {
                // 1) Resolver tipo de join.
                Join.Type type0 = join.type();

                // 2) Resolver tabla de join.
                Name name0 = new Name(join.table());

                // 3) Resolver alias.
                String alias0 = join.as();

                // 4) Resolver condicion de join.
                String on0 = join.on();

                // 5) Expresion de join.
                Join join0 = Joins.build(type0, name0, alias0, on0);
                expressions.add(join0);
            }
        }

        ctx.joins = expressions;
    }

    private static void prepareWhereClauses(PreparationContext ctx) {
        Expression whereClause;
        FgWhere[] wheres = AnnotationTypes.getAllWheres(ctx.klass);
        if (!Assertions.isNullOrEmpty(wheres)) {
            ExpressionBuilder builder = ExpressionBuilder.instance();
            builder = builder.TRUE();

            for (FgWhere where : wheres) {
                String value = where.value();
                if (!Assertions.stringNullOrEmpty(value)) {
                    builder = builder.and().lpar().wrap(value).rpar();
                }
            }

            whereClause = builder.build();
        } else {
            whereClause = null;
        }

        ctx.where = whereClause;
    }

    private static void prepareHavingClauses(PreparationContext ctx) {
        Expression havingClause;
        FgHaving[] havings = AnnotationTypes.getAllHavings(ctx.klass);
        if (!Assertions.isNullOrEmpty(havings)) {
            // 1) Inicializar condicion veradera.
            ExpressionBuilder builder = ExpressionBuilder.instance();
            builder = builder.TRUE();

            // 2) Incluir otras condiciones mediante operadores AND.
            for (FgHaving having : havings) {
                String value = having.value();
                if (!Assertions.stringNullOrEmpty(value)) {
                    builder = builder.and().lpar().wrap(value).rpar();
                }
            }

            // 3) Resolver expresion de filtro.
            havingClause = builder.build();
        } else {
            havingClause = null;
        }

        ctx.having = havingClause;
    }

    private static void prepareGroupClauses(PreparationContext ctx) {
        Collection<Name> names = Lists.empty();

        FgGroupBy[] groupBys = AnnotationTypes.getAllGroupBys(ctx.klass);
        if (!Assertions.isNullOrEmpty(groupBys)) {
            for (FgGroupBy groupBy : groupBys) {
                // 1) Resolver nombre de columna.
                Name name0 = new Name(groupBy.value());

                // 2) Clausula de agrupacion.
                names.add(name0);
            }
        }

        ctx.groupBy = names;
    }

    private static void prepareOrderClauses(PreparationContext ctx) {
        Collection<Order> orders = Lists.empty();

        FgOrderBy[] orderBys = AnnotationTypes.getAllOrderBys(ctx.klass);
        if (!Assertions.isNullOrEmpty(orderBys)) {
            for (FgOrderBy orderBy : orderBys) {
                // 1) Resolver tipo de ordenacion.
                Order.Type type0 = orderBy.type();

                // 2) Resolver criterio de ordenacion.
                Name name0 = new Name(orderBy.value());

                // 3) Clausula de ordenacion
                Order order = new Order(name0, type0);
                orders.add(order);
            }
        }

        ctx.orderBy = orders;
    }

    private static <T> SelectQuery buildSelectQuery(PreparationContext<T> ctx) {
        SelectQuery query = new SelectQuery();

        // CTEs.
        for (CTE cte : ctx.ctes) {
            query.cte(cte);
        }

        // From.
        query.from(ctx.table);

        // Proyecciones.
        for (Projection projection : ctx.projections) {
            query.projection(projection);
        }

        // Joins.
        for (Join join : ctx.joins) {
            query.join(join);
        }

        // Where.
        query.where(ctx.where);

        // Group by.
        for (Name name : ctx.groupBy) {
            query.groupBy(name);
        }

        // Having.
        query.having(ctx.having);

        // Order by.
        for (Order order : ctx.orderBy) {
            query.orderBy(order);
        }

        return query;
    }

    //<editor-fold defaultstate="collapsed" desc="Clases Auxiliares">
    private static class PreparationContext<T> {

        private Long counter;
        private Class<T> klass;
        private Collection<CTE> ctes;
        private Table table;
        private Collection<Projection> projections;
        private Collection<Join> joins;
        private Expression where;
        private Expression having;
        private Collection<Name> groupBy;
        private Collection<Order> orderBy;

        public PreparationContext(Class<T> klass) {
            this.counter = 0L;
            this.klass = klass;
        }

        public Long nextCount() {
            return counter++;
        }

        public Object resolveProjection(Field field, FgProjection projectionAnnotation) {
            Object projection0;
            if (projectionAnnotation.useRaw()) {
                projection0 = projectionAnnotation.value();
            } else {
                projection0 = new Name(projectionAnnotation.value());
            }
            return projection0;
        }

        public String resolveAlias(Field field, FgProjection projectionAnnotation) {
            if (Assertions.stringNullOrEmpty(projectionAnnotation.value()) && Assertions.stringNullOrEmpty(projectionAnnotation.as())) {
                return field.getName();
            }

            if (Assertions.stringNullOrEmpty(projectionAnnotation.as())) {
                return Strings.format("p%d", nextCount());
            } else {
                return projectionAnnotation.as();
            }
        }

    }
    //</editor-fold>

}
