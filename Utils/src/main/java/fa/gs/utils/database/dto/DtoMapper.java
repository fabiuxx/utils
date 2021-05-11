/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.dto.annotations.FgDto;
import fa.gs.utils.database.dto.annotations.FgPostConstruct;
import fa.gs.utils.database.dto.annotations.FgProjection;
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
     * Inicializador estatico.
     *
     * @param <T> Parametro de tipo.
     * @param klass Clase que se desea mapear.
     * @return Instancia de esta misma clase.
     */
    public static <T> DtoMapper<T> prepare(Class<T> klass) {
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
        validate(klass);

        // Inicializar datos.
        MappingContext ctx = new MappingContext(klass);
        prepareFieldMappings(ctx);

        mapper.klass = ctx.klass;
        mapper.mappings = ctx.mappings;
    }

    private static void validate(Class klass) {
        // Verificar que clase contenga anotacion DTO.
        FgDto dtoAnnotation = Reflection.getAnnotation(klass, AnnotationTypes.FGDTO);
        if (dtoAnnotation == null) {
            throw Errors.illegalArgument("Clase '%s' no es un DTO.", klass.getCanonicalName());
        }
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
                String alias0 = ctx.resolveAlias(field, projectionAnnotation);
                ctx.mappings.put(alias0, field);
            }
        }
    }

    public T[] select(String sql, EntityManager em) throws Throwable {
        QueryResultSetAdapter queryResultSetAdapter = new HibernateOrmResultSetAdapter();
        final Collection<Map<String, Object>> rows = queryResultSetAdapter.select(sql, em, mappings);
        return map(rows);
    }

    private T[] map(Collection<Map<String, Object>> rows) throws Throwable {
        Collection<T> instances = Lists.empty();
        for (Map<String, Object> row : rows) {
            T instance = map(row);
            instances.add(instance);
        }
        return Arrays.unwrap(instances, klass);
    }

    private T map(Map<String, Object> values) throws Throwable {
        if (Assertions.isNullOrEmpty(values)) {
            return null;
        }

        // Crear instancia y aplicar operacion de postconstruccion.
        Object instance = mapInstance(klass, mappings, values);
        postConstruct(klass, instance);
        return klass.cast(instance);
    }

    private Object mapInstance(Class klass, Map<String, Field> mappings, Map<String, Object> values) throws Throwable {
        Object instance = Reflection.createInstance(klass);
        if (instance == null) {
            throw Errors.illegalState("No se puede crear una instancia de '%s'", klass.getCanonicalName());
        }

        for (Map.Entry<String, Field> entry : mappings.entrySet()) {
            // Obtener nombre de mapeo y el campo concreto al cual se debe asignar el valor.
            String mappingName = entry.getKey();
            Object value = Maps.get(values, mappingName);

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
        private final Class<?> klass;
        private final Map<String, Field> mappings;

        public MappingContext(Class<?> klass) {
            this.counter = 0L;
            this.klass = klass;
            this.mappings = Maps.empty();
        }

        public Long nextCount() {
            return counter++;
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
