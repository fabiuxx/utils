/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.query.commands.Query;
import fa.gs.utils.database.query.commands.SelectCountQuery;
import fa.gs.utils.database.query.commands.SelectQuery;
import fa.gs.utils.database.query.expressions.EmptyExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.JoinExpression;
import fa.gs.utils.database.query.expressions.build.ConditionsExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.JoinExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.OrderExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.ProjectionExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.TableExpressionBuilder;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Numeric;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.text.Strings;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class DtoMapper<T> implements Serializable {

    private Class<T> klass;
    private Expression tableExpression;
    private Collection<Expression> joinClauses;
    private Collection<Expression> projectionExpressions;
    private Expression whereClause;
    private Expression havingClause;
    private Collection<Expression> orderClauses;
    private Map<String, Field> mappings;

    public static DtoMapper prepare(Class klass) {
        DtoMapper mapper = new DtoMapper();
        prepareInstance(mapper, klass);
        return mapper;
    }

    private static void prepareInstance(final DtoMapper mapper, Class klass) {
        // Validar definicion.
        validate(klass);

        // Inicializar datos.
        PreparationContext ctx = new PreparationContext();
        Map<String, Field> mappings = Maps.empty();

        // Procesar clase.
        Expression tableExpression = prepareTableExpression(ctx, klass);
        Collection<Expression> joinClauses = prepareTableJoinClauses(ctx, klass);
        Collection<Expression> projectionExpressions = prepareProjectionExpressions(ctx, klass, mappings);
        Expression whereClause = prepareWhereClauses(ctx, klass);
        Expression havingClause = prepareHavingClauses(ctx, klass);
        Collection<Expression> orderClauses = prepareOrderClauses(ctx, klass);

        // Completar instancia.
        mapper.klass = klass;
        mapper.joinClauses = joinClauses;
        mapper.tableExpression = tableExpression;
        mapper.projectionExpressions = projectionExpressions;
        mapper.whereClause = whereClause;
        mapper.havingClause = havingClause;
        mapper.orderClauses = orderClauses;
        mapper.mappings = mappings;
    }

    private static void validate(Class klass) {
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

    private static Collection<Expression> prepareProjectionExpressions(PreparationContext ctx, Class klass, Map<String, Field> mappings) {
        Collection<Expression> projections = Lists.empty();

        Collection<Field> declaredFields = Reflection.getAllFields(klass);
        for (Field field : declaredFields) {
            FgProjection projectionAnnotation = Reflection.getAnnotation(field, AnnotationTypes.FGPROJECTION);
            if (projectionAnnotation != null) {
                ProjectionExpressionBuilder builder = ProjectionExpressionBuilder.instance();
                // Proyeccion.
                builder.name(projectionAnnotation.value());

                // Alias.
                String alias0 = ctx.resolveAlias(projectionAnnotation);
                builder.as(alias0);

                // Expression de proyeccion.
                Expression projection = builder.build();
                projections.add(projection);

                // Mapeo.
                mappings.put(alias0, field);
            }
        }

        return projections;
    }

    private static Expression prepareTableExpression(PreparationContext ctx, Class klass) {
        FgDto annotation = Reflection.getAnnotation(klass, AnnotationTypes.FGDTO);
        TableExpressionBuilder builder = TableExpressionBuilder.instance();

        // Nombre de tabla.
        builder.name(annotation.table());

        // Alias.
        if (!Assertions.stringNullOrEmpty(annotation.as())) {
            builder.as(annotation.as());
        }

        return builder.build();
    }

    private static Collection<Expression> prepareTableJoinClauses(PreparationContext ctx, Class klass) {
        Collection<Expression> expressions = Lists.empty();

        FgJoin[] joins = AnnotationTypes.getAllJoins(klass);
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

        return expressions;
    }

    private static Expression prepareWhereClauses(PreparationContext ctx, Class klass) {
        FgWhere[] wheres = AnnotationTypes.getAllWheres(klass);
        if (!Assertions.isNullOrEmpty(wheres)) {
            ConditionsExpressionBuilder builder = ConditionsExpressionBuilder.instance();
            builder = builder.TRUE();

            for (FgWhere where : wheres) {
                String value = where.value();
                if (!Assertions.stringNullOrEmpty(value)) {
                    builder = builder.and().lpar().raw(value).rpar();
                }
            }

            return builder.build();
        } else {
            return EmptyExpression.instance();
        }
    }

    private static Expression prepareHavingClauses(PreparationContext ctx, Class klass) {
        FgHaving[] havings = AnnotationTypes.getAllHavings(klass);
        if (!Assertions.isNullOrEmpty(havings)) {
            ConditionsExpressionBuilder builder = ConditionsExpressionBuilder.instance();
            builder = builder.TRUE();

            for (FgHaving where : havings) {
                String value = where.value();
                if (!Assertions.stringNullOrEmpty(value)) {
                    builder = builder.and().lpar().raw(value).rpar();
                }
            }

            return builder.build();
        } else {
            return EmptyExpression.instance();
        }
    }

    private static Collection<Expression> prepareOrderClauses(PreparationContext ctx, Class klass) {
        Collection<Expression> expressions = Lists.empty();

        FgOrderBy[] orderByS = AnnotationTypes.getAllOrderBys(klass);
        if (!Assertions.isNullOrEmpty(orderByS)) {
            for (FgOrderBy orderBy : orderByS) {
                OrderExpressionBuilder builder = OrderExpressionBuilder.instance();
                builder.type(orderBy.type());
                builder.name(orderBy.value());

                Expression expression = builder.build();
                expressions.add(expression);
            }
        }

        return expressions;
    }

    public Class<T> getDtoClass() {
        return klass;
    }

    public SelectQuery getSelectQuery() {
        SelectQuery query = new SelectQuery();

        // Expresion de tabla principal.
        query.from().wrap(tableExpression);

        // Clausulas join.
        joinClauses.forEach(exp -> query.join().wrap(exp));

        // Criterios de filtrad.
        projectionExpressions.forEach(exp -> query.projection().wrap(exp));

        // Clausula where.
        query.where().wrap(whereClause);

        // Clausula having.
        query.having().wrap(havingClause);

        // Criterios de ordenacion.
        orderClauses.forEach((orderBy) -> query.order().wrap(orderBy));

        return query;
    }

    public SelectCountQuery getSelectCountQuery() {
        SelectCountQuery query = new SelectCountQuery();

        // Expresion de tabla principal.
        query.from().wrap(tableExpression);

        // Clausulas join.
        joinClauses.forEach(exp -> query.join().wrap(exp));

        // Clausula where.
        query.where().wrap(whereClause);

        // Clausula having.
        query.having().wrap(havingClause);

        return query;
    }

    public T[] select(EntityManager em) throws Throwable {
        Query query = getSelectQuery();
        return select(query.stringify(null), em);
    }

    public T[] select(String sql, EntityManager em) throws Throwable {
        // Ejecutar la query e indicar que necesitamos mapear el resultset a un mapa, valga la redundancia.
        org.hibernate.Query hibernateQuery = em.createNativeQuery(sql)
                .unwrap(org.hibernate.Query.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        // Mapear result set.
        Collection<T> instances = Lists.empty();
        Collection<Map<String, Object>> resultsSet = (Collection<Map<String, Object>>) hibernateQuery.list();
        for (Map<String, Object> resultSet : resultsSet) {
            // Crear instancia y aplicar operacion de postconstruccion.
            Object instance = mapInstance(klass, mappings, resultSet);
            postConstruct(klass, instance);
            instances.add(klass.cast(instance));
        }

        return Arrays.array(instances, klass);
    }

    public Long count(EntityManager em) throws Throwable {
        Query query = getSelectCountQuery();
        return count(query.stringify(null), em);
    }

    public Long count(String sql, EntityManager em) throws Throwable {
        // Ejecutar la query e indicar que necesitamos mapear el resultset a un mapa, valga la redundancia.
        org.hibernate.Query hibernateQuery = em.createNativeQuery(sql)
                .unwrap(org.hibernate.Query.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        // Mapear result set.
        Collection<Map<String, Object>> resultsSet = (Collection<Map<String, Object>>) hibernateQuery.list();
        Map<String, Object> resultSet = Lists.first(resultsSet);
        Object count0 = Maps.get(resultSet, SelectCountQuery.COUNT_FIELD_NAME);
        return Numeric.adaptAsLong(count0);
    }

    private Object mapInstance(Class klass, Map<String, Field> mappings, Map<String, Object> values) throws Throwable {
        Map<Class<? extends FgProjectionResultConverter>, FgProjectionResultConverter> converters = Maps.empty();

        Object instance = Reflection.createInstanceUnsafe(klass);

        for (Map.Entry<String, Field> entry : mappings.entrySet()) {
            // Crear instancia de convertidor, si hubiere.
            FgProjectionResultConverter converter = null;
            FgProjection projectionInfo = Reflection.getAnnotation(entry.getValue(), FgProjection.class);
            if (projectionInfo != null && projectionInfo.converter() != null) {
                Class<? extends FgProjectionResultConverter> converterClass = projectionInfo.converter();
                // Omitir el valor por defecto de la anotacion (una interface no instanciable).
                if (!Objects.equals(converterClass, FgProjectionResultConverter.class)) {
                    if (!converters.containsKey(converterClass)) {
                        converter = createConverterInstance(converterClass);
                        converters.put(converterClass, converter);
                    } else {
                        converter = converters.get(converterClass);
                    }
                }
            }

            // Obtener nombre de mapeo y valor correspondiente.
            String mappingName = entry.getKey();
            Object value = Maps.get(values, mappingName);

            // Utilizar convertidor de valores, si hubiere.
            if (converter != null) {
                value = converter.convert(value);
            }

            // Verificar que valor sea asignable a campo.
            assertValueIsAssignable(entry.getValue(), value);

            // Asignar valor.
            Reflection.set(instance, mappings.get(mappingName), value);
        }

        return instance;
    }

    private void postConstruct(Class<?> klass, Object instance) {
        Method postConstruct = Reflection.getAnnotatedMethod(klass, FgPostConstruct.class);
        if (Reflection.isCallable(postConstruct)) {
            try {
                postConstruct.setAccessible(true);
                postConstruct.invoke(instance);
            } catch (Throwable thr) {
                throw new IllegalArgumentException(String.format("Método '%s' de clase '%s' no se puede ejecutar.", postConstruct.getName(), klass.getCanonicalName()));
            }
        }
    }

    private FgProjectionResultConverter createConverterInstance(Class<? extends FgProjectionResultConverter> klass) throws Throwable {
        Object instance;

        try {
            instance = Reflection.createInstance(klass);
            return klass.cast(instance);
        } catch (Throwable thr) {
            instance = Reflection.createInstanceUnsafe(klass);
            return klass.cast(instance);
        }
    }

    private static void assertIsDtoAnnotated(FgDto annotation, Class klass) {
        if (annotation == null) {
            throw new IllegalArgumentException(String.format("Clase '%s' no es un DTO.", klass.getCanonicalName()));
        }
    }

    private static void assertDtoAnnotationHasTable(FgDto annotation, Class klass) {
        if (Assertions.stringNullOrEmpty(annotation.table())) {
            throw new IllegalArgumentException(String.format("Clase '%s' no posee un nombre de tabla.", klass.getCanonicalName()));
        }
    }

    private static void assertValueIsAssignable(Field field, Object value) {
        if (value != null) {
            Class<?> fieldType = field.getType();
            Class<?> valueType = value.getClass();
            boolean isAssignable = fieldType.isAssignableFrom(valueType);
            if (!isAssignable) {
                String n0 = fieldType.getCanonicalName();
                String c0 = field.getName();
                String n1 = valueType.getCanonicalName();
                throw new IllegalArgumentException(String.format("No se puede asignar un valor de tipo '%s' al campo '%s' de tipo '%s'.", n1, c0, n0));
            }
        }
    }

    private static void assertDtoAnnotationHasAlias(FgDto annotation, Class klass) {
        if (Assertions.stringNullOrEmpty(annotation.as())) {
            throw new IllegalArgumentException(String.format("Clase '%s' no posee un alias.", klass.getCanonicalName()));
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Serializacion/Deserializacion">
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        String className = klass.getCanonicalName();
        out.writeUTF(className);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        String className = in.readUTF();
        Class<?> klass0 = Class.forName(className);
        prepareInstance(this, klass0);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Clases Auxiliares">
    static class PreparationContext {

        private Long counter;

        public PreparationContext() {
            counter = 0L;
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

    static class DtoNode {

        Class klass;
        Field field;
        String table;
        String alias;
        Long order;
        JoinInfo join;
        final List<ProjectionInfo> projections;
        final List<DtoNode> childs;

        DtoNode(Class klass, Field field) {
            this.klass = klass;
            this.field = field;
            this.table = "";
            this.alias = "";
            this.order = 0L;
            this.projections = Lists.empty();
            this.join = null;
            this.childs = Lists.empty();
        }

    }

    static class ProjectionInfo {

        Field field;
        String projection;
        String alias;
        Long order;

        ProjectionInfo() {
            this.projection = "";
            this.alias = "";
            this.order = 0L;
        }

    }

    static class JoinInfo {

        JoinExpression.Type joinType;
        String joinAlias;
        String joinOn;

        JoinInfo() {
            this.joinType = JoinExpression.Type.NORMAL;
            this.joinAlias = "";
            this.joinOn = "";
        }

    }
    //</editor-fold>

}
