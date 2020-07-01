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
import fa.gs.utils.database.dto.annotations.FgQueryResultSetAdapter;
import fa.gs.utils.database.dto.converters.DtoValueConverter;
import fa.gs.utils.database.dto.converters.DtoValueConverterTarget;
import fa.gs.utils.database.dto.mapping.HibernateOrmResultSetAdapter;
import fa.gs.utils.database.dto.mapping.QueryResultSetAdapter;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Strings;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class DtoMapper<T> implements Serializable {

    /**
     * Clase de la cual se obtienen los campos con definiciones de proyeccion de
     * tablas y columnas.
     */
    private Class<T> klass;

    /**
     * Mapeos entre campos y columnas de resultset.
     */
    private Map<String, Field> mappings;

    /**
     * Adaptador de resultset de base de datos a valores escalares.
     */
    private QueryResultSetAdapter queryResultSetAdapter;

    /**
     * Inicializador estatico.
     *
     * @param klass Clase que se desea mapear.
     * @return Instancia de esta misma clase.
     */
    public static DtoMapper prepare(Class klass) {
        DtoMapper mapper = new DtoMapper();
        prepareInstance(mapper, klass);
        return mapper;
    }

    /**
     * Inicializa una instancia de mapper en base a una clase dada.
     *
     * @param mapper Instancia de mapeador de proyecciones.
     * @param klass Clase con definiciones de proyecciones.
     */
    private static void prepareInstance(final DtoMapper mapper, Class klass) {
        // Validar definicion.
        DtoQuery.validate(klass);

        // Inicializar datos.
        MappingContext ctx = new MappingContext(klass);
        prepareFieldMappings(ctx);
        prepareMappingStrategy(ctx);

        mapper.klass = ctx.klass;
        mapper.mappings = ctx.mappings;
        mapper.queryResultSetAdapter = ctx.queryResultSetAdapter;
    }

    /**
     * Procesa las definiciones de mapeo de proyecciones.
     *
     * @param ctx Contexto de mapeo.
     */
    private static void prepareFieldMappings(MappingContext ctx) {
        // Obtener todos los campos (incluso los heredados) definidos en la clase a mapear.
        Collection<Field> declaredFields = Reflection.getAllFields(ctx.klass);
        for (Field field : declaredFields) {
            FgProjection projectionAnnotation = Reflection.getAnnotation(field, AnnotationTypes.FGPROJECTION);
            if (projectionAnnotation != null) {
                // Mapeo de alias a campo concreto.
                String alias0 = ctx.resolveAlias(projectionAnnotation);
                ctx.mappings.put(alias0, field);
            }
        }
    }

    /**
     * Procesa la estrategia de adaptacion de valores de resultset a objetos de
     * datos.
     *
     * @param ctx Contexto de mapeo.
     */
    private static void prepareMappingStrategy(MappingContext ctx) {
        // Crear instancia de estrategia de adaptacion de result sets de base de datos, si hubiere.
        QueryResultSetAdapter adapter = null;
        FgQueryResultSetAdapter queryAdapterAnnotation = Reflection.getAnnotation(ctx.klass, AnnotationTypes.FGQUERYRSADAPTER);
        if (queryAdapterAnnotation != null && queryAdapterAnnotation.adapter() != null) {
            Class<? extends QueryResultSetAdapter> adapterClass = queryAdapterAnnotation.adapter();
            adapter = Reflection.tryCreateInstance(adapterClass);
            if (adapter == null) {
                throw Errors.illegalArgument("Clase '%s' no es instanciable.", adapterClass);
            }
        }

        // Crear estrategia por defecto.
        if (adapter == null) {
            adapter = new HibernateOrmResultSetAdapter();
        }

        ctx.queryResultSetAdapter = adapter;
    }

    public T[] select(String sql, EntityManager em) throws Throwable {
        Collection<T> instances = Lists.empty();

        final Collection<Map<String, Object>> rows = queryResultSetAdapter.select(sql, em);
        for (Map<String, Object> row : rows) {
            // Crear instancia y aplicar operacion de postconstruccion.
            Object instance = mapInstance(klass, mappings, row);
            postConstruct(klass, instance);
            instances.add(klass.cast(instance));
        }

        return Arrays.unwrap(instances, klass);
    }

    private Object mapInstance(Class klass, Map<String, Field> mappings, Map<String, Object> values) throws Throwable {
        Map<Class<? extends DtoValueConverter>, DtoValueConverter> converters = Maps.empty();

        Object instance = Reflection.createInstance(klass);
        if (instance == null) {
            throw Errors.illegalState("No se puede crear una instancia de '%s'", klass.getCanonicalName());
        }

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
        private QueryResultSetAdapter queryResultSetAdapter;

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
