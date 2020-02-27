/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.query.commands.Query;
import fa.gs.utils.database.query.commands.SelectQuery;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.JoinExpression;
import fa.gs.utils.database.query.expressions.build.JoinExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.ProjectionExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.TableExpressionBuilder;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Reflect;
import fa.gs.utils.misc.text.Strings;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Fabio A. González Sosa
 */
public class DtoMapper<T> implements Serializable {

    private Class<T> klass;
    private Expression tableExpression;
    private Collection<Expression> joinClauses;
    private Collection<Expression> projectionExpressions;
    private Map<String, Field> mappings;

    public static DtoMapper prepare(Class klass) {
        // Validar definicion.
        validate(klass);

        PreparationContext ctx = new PreparationContext();
        Map<String, Field> mappings = Maps.empty();

        Expression tableExpression = prepareTableExpression(ctx, klass);
        Collection<Expression> joinClauses = prepareTableJoinClauses(ctx, klass);
        Collection<Expression> projectionExpressions = prepareProjectionExpressions(ctx, klass, mappings);

        DtoMapper mapper = new DtoMapper();
        mapper.klass = klass;
        mapper.joinClauses = joinClauses;
        mapper.tableExpression = tableExpression;
        mapper.projectionExpressions = projectionExpressions;
        mapper.mappings = mappings;
        return mapper;
    }

    private static void validate(Class klass) {
        // Verificar que clase contenga anotacion DTO.
        FgDto dtoAnnotation = Reflect.getAnnotation(klass, AnnotationTypes.FGDTO);
        assertIsDtoAnnotated(dtoAnnotation, klass);

        // Verificar que DTO tenga un nombre de tabla.
        assertDtoAnnotationHasTable(dtoAnnotation, klass);

        /**
         * Si existen clausulas JOIN, verificar que DTO principal posea un
         * alias.
         */
        FgJoins joinAnnotations = Reflect.getAnnotation(klass, AnnotationTypes.FGJOINS);
        if (joinAnnotations != null) {
            assertDtoAnnotationHasAlias(dtoAnnotation, klass);
        }
    }

    private static Collection<Expression> prepareProjectionExpressions(PreparationContext ctx, Class klass, Map<String, Field> mappings) {
        Collection<Expression> projections = Lists.empty();
        for (Field field : klass.getDeclaredFields()) {
            FgProjection projectionAnnotation = Reflect.getAnnotation(field, AnnotationTypes.FGPROJECTION);
            if (projectionAnnotation != null) {
                ProjectionExpressionBuilder builder = new ProjectionExpressionBuilder();
                // Proyeccion.
                builder.name(projectionAnnotation.name());

                // Alias.
                String alias0;
                if (Assertions.stringNullOrEmpty(projectionAnnotation.as())) {
                    alias0 = ctx.nextAlias();
                } else {
                    alias0 = projectionAnnotation.as();
                }
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
        FgDto annotation = Reflect.getAnnotation(klass, AnnotationTypes.FGDTO);
        TableExpressionBuilder builder = new TableExpressionBuilder();

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
        FgJoins joins = Reflect.getAnnotation(klass, AnnotationTypes.FGJOINS);
        if (joins != null && Assertions.isNullOrEmpty(joins.value()) == false) {
            for (FgJoin join : joins.value()) {
                JoinExpressionBuilder builder = new JoinExpressionBuilder();
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

    public SelectQuery getSelectQuery() {
        SelectQuery query = new SelectQuery();

        query.from().wrap(tableExpression);

        for (Expression exp : joinClauses) {
            query.join().wrap(exp);
        }

        for (Expression proj : projectionExpressions) {
            query.projection().wrap(proj);
        }

        return query;
    }

    public Collection<T> select(EntityManager em) throws Throwable {
        return select(getSelectQuery(), em);
    }

    public Collection<T> select(Query query, EntityManager em) throws Throwable {
        // Convertir query a una cadena de texto.
        String sql = query.stringify(null);

        // Ejecutar la query e indicar que necesitamos mapear el resultset a un mapa, valga la redundancia.
        org.hibernate.Query hibernateQuery = em.createNativeQuery(sql)
                .unwrap(org.hibernate.Query.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        // Mapear result set.
        Collection<T> instances = Lists.empty();
        Collection<Map<String, Object>> resultsSet = (Collection<Map<String, Object>>) hibernateQuery.list();
        for (Map<String, Object> resultSet : resultsSet) {
            Object instance = mapInstance(klass, mappings, resultSet);
            instances.add(klass.cast(instance));
        }

        return instances;
    }

    private Object mapInstance(Class klass, Map<String, Field> mappings, Map<String, Object> values) throws Throwable {
        Map<Class<? extends FgProjectionResultConverter>, FgProjectionResultConverter> converters = Maps.empty();

        Object instance = Reflect.createInstanceUnsafe(klass);

        for (Map.Entry<String, Field> entry : mappings.entrySet()) {
            // Crear instancia de convertidor, si hubiere.
            FgProjectionResultConverter converter = null;
            FgProjection projectionInfo = Reflect.getAnnotation(entry.getValue(), FgProjection.class);
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

            // Asignar valor.
            Reflect.set(instance, mappings.get(mappingName), value);
        }

        return instance;
    }

    private FgProjectionResultConverter createConverterInstance(Class<? extends FgProjectionResultConverter> klass) throws Throwable {
        Object instance;

        try {
            instance = Reflect.createInstance(klass);
            return klass.cast(instance);
        } catch (Throwable thr) {
            instance = Reflect.createInstanceUnsafe(klass);
            return klass.cast(instance);
        }
    }

    private static void assertIsDtoAnnotated(FgDto annotation, Class klass) {
        if (annotation == null) {
            throw new IllegalArgumentException(String.format("Clase '%s' no es un DTO.", klass.getCanonicalName()));
        }
    }

    private static void assertDtoAnnotationHasTable(FgDto annotation, Class klass) {
        if (Assertions.stringNullOrEmpty(annotation.as())) {
            throw new IllegalArgumentException(String.format("Clase '%s' no posee un nombre de tabla.", klass.getCanonicalName()));
        }
    }

    private static void assertDtoAnnotationHasAlias(FgDto annotation, Class klass) {
        if (Assertions.stringNullOrEmpty(annotation.as())) {
            throw new IllegalArgumentException(String.format("Clase '%s' no posee un alias.", klass.getCanonicalName()));
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Clases Auxiliares">
    static class PreparationContext {

        private Long counter;

        public PreparationContext() {
            counter = 0L;
        }

        public Long nextCount() {
            return counter++;
        }

        public String nextAlias() {
            return Strings.format("p%d", nextCount());
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
