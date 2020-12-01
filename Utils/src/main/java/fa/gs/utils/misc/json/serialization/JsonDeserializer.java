/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.json.Json;
import fa.gs.utils.misc.json.adapter.JsonAdapterFromJson;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.misc.text.Text;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonDeserializer {

    public static Boolean DEBUG = false;

    private static void debug(String fmt, Object... args) {
        if (DEBUG) {
            System.out.println(Strings.format(fmt, args));
        }
    }

    public static <T> T deserialize(String json0, Class<T> targetClass) throws Throwable {
        JsonElement json = Json.fromString(json0);
        T value = deserialize(json, targetClass);
        return value;
    }

    public static <T> T deserialize(JsonElement json, Class<T> targetClass) throws Throwable {
        debug("[DESERIALIZE START] target=%s", targetClass.getCanonicalName());
        final DeserializationContext ctx = new DeserializationContext();
        Object instance0 = resolveElement(ctx, json, targetClass, null);
        T value = targetClass.cast(instance0);
        debug("[DESERIALIZE END]");
        return value;
    }

    private static Object resolveElement(final DeserializationContext ctx, JsonElement element, Class<?> targetClass, Field field) throws Throwable {
        // Control de seguridad.
        if (element == null || element.isJsonNull()) {
            return null;
        }

        // Procesamiento de valores primitivos.
        if (GsonUtils.isGsonPrimitive(targetClass)) {
            return resolvePrimitive(ctx, element, targetClass);
        }

        // Procesamiento de coleccion de objetos.
        if (element.isJsonArray()) {
            if (Reflection.isCollection(targetClass)) {
                return resolveCollection(ctx, element, targetClass, field);
            } else if (Reflection.isArray(targetClass)) {
                return resolveArray(ctx, element, targetClass, field);
            } else {
                throw Errors.illegalState("Tipo de elemento destino no procesable como array o coleccion de objetos.");
            }
        }

        // Procesamiento de objeto.
        if (element.isJsonObject()) {
            return resolveObject(ctx, element, targetClass);
        }

        // Error.
        throw Errors.illegalState("Tipo de elemento no procesable.");
    }

    /**
     * Reduce un elemento JSON a su equivalente valor primitivo.
     *
     * @param ctx Contexto de deserializacion.
     * @param element Elemento JSON, que debe ser instancia de
     * {@link com.google.gson.JsonPrimitive JsonPrimitive}.
     * @param targetClass Clase objetivo.
     * @return Valor reducido.
     * @throws Throwable Si el elemento JSON no es primitivo o si la clase
     * objetivo no representa un tipo primitivo de Java.
     */
    private static Object resolvePrimitive(final DeserializationContext ctx, JsonElement element, Class<?> targetClass) throws Throwable {
        debug("PRIMITIVE isPrimitive=%s target=%s", element.isJsonPrimitive(), targetClass.getCanonicalName());

        if (!element.isJsonPrimitive()) {
            throw Errors.illegalArgument("La clase '%s' no acepta valores json no primitivos.", targetClass.getCanonicalName());
        }

        Object value = ctx.gson.fromJson(element, targetClass);
        debug("OK");
        return value;
    }

    /**
     * Reduce un elemento JSON a una coleccion de objetos.
     *
     * @param ctx Contexto de deserializacion.
     * @param element Elemento JSON a reducir.
     * @param targetClass Clase objetivo,
     * @param field
     * @return Coleccion de objetos encapsulados dentro de un
     * {@link java.util.ArrayList ArrayList}.
     * @throws Throwable
     */
    private static Object resolveCollection(final DeserializationContext ctx, JsonElement element, Class<?> targetClass, Field field) throws Throwable {
        if (!Reflection.isCollection(targetClass)) {
            throw Errors.illegalArgument("La clase '%s' debe ser una colección.", targetClass.getCanonicalName());
        }

        if (field == null) {
            throw Errors.illegalArgument("Se esperaba una definición de atributo para resolver el array de objetos json.");
        }

        debug("COLLECTION target=%s field=%s", targetClass.getCanonicalName(), field.getName());
        final JsonArray array = element.getAsJsonArray();
        final Collection collection = new ArrayList<>(array.size());
        Class<?> genericType = Reflection.getFirstActualGenericType(field.getGenericType());
        debug("    genericType=%s", genericType.getCanonicalName());
        for (JsonElement arrayElement : array) {
            Object instance = resolveElement(ctx, arrayElement, genericType, field);
            collection.add(instance);
        }

        return collection;
    }

    /**
     * Reduce un elemento JSON a una coleccion de objetos.
     *
     * @param ctx Contexto de deserializacion.
     * @param element Elemento JSON a reducir.
     * @param targetClass Clase objetivo,
     * @param field
     * @return Coleccion de objetos encapsulados dentro de un
     * {@link java.util.ArrayList ArrayList}.
     * @throws Throwable
     */
    private static Object[] resolveArray(final DeserializationContext ctx, JsonElement element, Class<?> targetClass, Field field) throws Throwable {
        if (!Reflection.isArray(targetClass)) {
            throw Errors.illegalArgument("La clase '%s' debe ser un array.", targetClass.getCanonicalName());
        }

        if (field == null) {
            throw Errors.illegalArgument("Se esperaba una definición de atributo para resolver el array de objetos json.");
        }

        debug("ARRAY target=%s field=%s", targetClass.getCanonicalName(), field.getName());
        final JsonArray array = element.getAsJsonArray();
        final Collection collection = new ArrayList<>(array.size());
        Class<?> arrayType = targetClass.getComponentType();
        debug("    arrayType=%s", arrayType.getCanonicalName());
        for (JsonElement arrayElement : array) {
            Object instance = resolveElement(ctx, arrayElement, arrayType, field);
            collection.add(instance);
        }

        return Arrays.unwrap(collection, arrayType);
    }

    /**
     * Reduce un elemento JSON a un POJO.
     *
     * @param ctx Contexto de deserializacion.
     * @param element Elemento JSON, que debe ser instancia de
     * {@link com.google.gson.JsonObject JsonObject}.
     * @param targetClass
     * @return Valor reducido.
     * @throws Throwable Cuando no se puede reducir el elemento JSON a un POJO,
     * porque:
     * <ul>
     * <li>El elemento JSON es un valor primitivo.</li>
     * <li>No se puede crear una instancia de la clase objetivo.</li>
     * <li>Los tipos para los atributos definidos en la clase objetivo no
     * concuerdan con los tipos de sus correspondientes valores reducidos.</li>
     * <li>No se puede asignar un valor reducido a un atributo de la clase
     * objetivo.</li>
     * </ul>
     */
    private static Object resolveObject(final DeserializationContext ctx, JsonElement element, Class<?> targetClass) throws Throwable {
        // Control.
        if (element.isJsonPrimitive()) {
            throw Errors.illegalArgument("La clase '%s' no acepta valores json primitivos.", targetClass.getCanonicalName());
        }

        debug("OBJECT target=%s", targetClass.getCanonicalName());

        // Instanciar nuevo objeto.
        Object instance = Reflection.createInstance(targetClass);
        if (instance == null) {
            throw Errors.illegalArgument("No se pudo crear una instancia de '%s'.", targetClass.getCanonicalName());
        }

        // Procesar atributos anotados como propiedades.
        Collection<Field> declaredFields = Reflection.getAllFields(targetClass);
        for (Field field : declaredFields) {
            JsonProperty annotation = Reflection.getAnnotation(field, JsonProperty.class);
            if (annotation != null) {
                // Resolver propiedad de objeto json.
                Object resolvedProperty = resolveObjectProperty(ctx, element, field, annotation);
                if (resolvedProperty != null && !Reflection.isInstanceOf(resolvedProperty.getClass(), field.getType())) {
                    throw Errors.illegalArgument("No se pudo establecer el valor resuelto para la propiedad '%s'. No se puede convertir '%s' a '%s'.", field.getName(), resolvedProperty.getClass().getCanonicalName(), field.getType().getCanonicalName());
                }

                // Asignar valor de propiedad.
                boolean ok = Reflection.set(instance, field, resolvedProperty);
                if (!ok) {
                    throw Errors.illegalArgument("No se pudo establecer el valor resuelto para la propiedad '%s' en la clase '%s'", field.getName(), targetClass.getCanonicalName());
                }
            }
        }

        // Procesamiento de postconstruccion.
        Method method = Reflection.getAnnotatedMethod(instance.getClass(), JsonPostConstruct.class);
        if (Reflection.isCallable(method)) {
            method.setAccessible(true);
            method.invoke(instance);
        }

        return instance;
    }

    /**
     * Reduce un elemento JSON a un valor que sera asignado a un atributo de
     * instancia arbitraria.
     *
     * @param ctx Contexto de deserializacion.
     * @param element Elemento JSON a reducir.
     * @param field Atributo dentro de instancia.
     * @param annotation Annotacion con descripciones base sobre como resolver
     * la propiedad JSON.
     * @return Valor reducido.
     */
    private static Object resolveObjectProperty(final DeserializationContext ctx, JsonElement element, Field field, JsonProperty annotation) {
        // Resolver propiedad en objeto json.
        String propertyName = Text.select(annotation.name(), field.getName());
        JsonElement property = Json.resolvePath(element.getAsJsonObject(), propertyName);

        debug("OBJECT PROPERTY property=%s field=%s", propertyName, field.getName());

        // Control de tipo de resolucion.
        JsonResolution resolution = annotation.resolution();
        debug("    resolution=%s", String.valueOf(resolution));
        if (resolution == JsonResolution.MANDATORY && property == null) {
            throw Errors.illegalArgument("La propiedad json '%s' es obligatoria para la clase '%s'.", propertyName, field.getDeclaringClass().getCanonicalName());
        }
        if (resolution == JsonResolution.OPTIONAL && property == null) {
            return null;
        }

        debug("    json=%s", Json.toString(element));

        try {
            // Utilizar adaptador, si hubiere. Los adaptadores tienen preferencia.
            JsonAdapterFromJson<Object> adapter = ctx.getAdapter(annotation.fromJsonAdapter());
            if (adapter != null) {
                return adapter.adapt(property);
            } else {
                return resolveElement(ctx, property, field.getType(), field);
            }
        } catch (Throwable thr) {
            throw Errors.illegalArgument(thr, "No es posible resolver la propiedad '%s' para la clase '%s'.", propertyName, field.getDeclaringClass().getCanonicalName());
        }
    }

    private static class DeserializationContext {

        final Gson gson;
        final Map<Class, JsonAdapterFromJson> adapters;

        private DeserializationContext() {
            this.gson = new Gson();
            this.adapters = Maps.empty();
        }

        private JsonAdapterFromJson getAdapter(Class<? extends JsonAdapterFromJson> adapterClass) throws Throwable {
            // Control de seguridad.
            if (adapterClass == null || adapterClass.equals(JsonAdapterFromJson.class)) {
                return null;
            }

            if (!adapters.containsKey(adapterClass)) {
                JsonAdapterFromJson converter = Reflection.tryCreateInstance(adapterClass);
                adapters.put(adapterClass, converter);
            }

            return adapters.get(adapterClass);
        }
    }

}
