/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.dto.annotations.FgConverter;
import fa.gs.utils.database.dto.annotations.FgPostConstruct;
import fa.gs.utils.database.dto.annotations.FgProjection;
import fa.gs.utils.database.dto.converters.DtoValueConverter;
import fa.gs.utils.database.dto.converters.DtoValueConverterTarget;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.build.ProjectionExpressionBuilder;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Strings;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class DtoMapper<T> {

    private Class<T> klass;
    private Map<String, Field> mappings;

    public static DtoMapper prepare(Class klass) {
        DtoMapper mapper = new DtoMapper();
        prepareInstance(mapper, klass);
        return mapper;
    }

    private static void prepareInstance(final DtoMapper mapper, Class klass) {
        // Validar definicion.
        DtoQuery.validate(klass);

        // Inicializar datos.
        MappingContext ctx = new MappingContext(klass);
        prepareMappings(ctx);

        mapper.klass = ctx.klass;
        mapper.mappings = ctx.mappings;
    }

    private static Collection<Expression> prepareMappings(MappingContext ctx) {
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

                // Mapeo.
                ctx.mappings.put(alias0, field);
            }
        }

        return projections;
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

    private Object mapInstance(Class klass, Map<String, Field> mappings, Map<String, Object> values) throws Throwable {
        Map<Class<? extends DtoValueConverter>, DtoValueConverter> converters = Maps.empty();

        Object instance = Reflection.createInstanceUnsafe(klass);

        for (Map.Entry<String, Field> entry : mappings.entrySet()) {
            // Crear instancia de convertidor, si hubiere.
            DtoValueConverter converter = null;
            FgConverter converterAnnotation = Reflection.getAnnotation(entry.getValue(), AnnotationTypes.FGCONVERTER);
            if (converterAnnotation != null && converterAnnotation.converter() != null) {
                Class<? extends DtoValueConverter> converterClass = converterAnnotation.converter();
                // Omitir el valor por defecto de la anotacion, ya que esta representa una interfaz no instanciable.
                if (!Objects.equals(converterClass, DtoValueConverter.class)) {
                    if (!converters.containsKey(converterClass)) {
                        converter = Reflection.tryCreateInstance(converterClass);
                        converters.put(converterClass, converter);
                    } else {
                        converter = converters.get(converterClass);
                    }
                }
            }

            // Obtener nombre de mapeo y el campo concreto al cual se debe asignar el valor.
            String mappingName = entry.getKey();
            Object value = Maps.get(values, mappingName);

            // Utilizar convertidor de valores, si hubiere.
            if (converter != null) {
                DtoValueConverterTarget conversionTarget = converter.target();
                if (conversionTarget == DtoValueConverterTarget.ROW) {
                    // Convertir toda la fila.
                    value = converter.convert(values);
                } else if (conversionTarget == DtoValueConverterTarget.PROJECTION) {
                    // Convertir proyeccion.
                    value = converter.convert(value);
                } else {
                    // No deberia ocurrir.
                    throw Errors.illegalState();
                }
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
                throw Errors.illegalArgument("Método '%s' de clase '%s' no se puede ejecutar.", postConstruct.getName(), klass.getCanonicalName());
            }
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
                throw Errors.illegalArgument("No se puede asignar un valor de tipo '%s' al campo '%s' de tipo '%s'.", n1, c0, n0);
            }
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
    static class MappingContext {

        private Long counter;
        private final Class klass;
        private final Map<String, Field> mappings;

        public MappingContext(Class klass) {
            this.counter = 0L;
            this.klass = klass;
            this.mappings = Maps.empty();
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
