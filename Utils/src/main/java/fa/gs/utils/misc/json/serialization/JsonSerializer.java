/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.json.JsonArrayBuilder;
import fa.gs.utils.misc.json.JsonObjectBuilder;
import fa.gs.utils.misc.json.adapter.JsonAdapterToJson;
import fa.gs.utils.misc.text.Text;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonSerializer {

    public static JsonElement serialize(Object instance) throws Throwable {
        //Utils.checkIsJsonProcessable(instance);
        final JsonSerializer.SerializationContext ctx = new JsonSerializer.SerializationContext();
        return resolveInstance(ctx, instance, null);
    }

    private static JsonElement resolveInstance(SerializationContext ctx, Object instance, Field field) throws Throwable {
        // Control de seguridad.
        if (instance == null) {
            return JsonNull.INSTANCE;
        }

        Class sourceClass = instance.getClass();

        // Procesamiento de valores primitivos.
        if (GsonUtils.isGsonPrimitive(sourceClass)) {
            return resolvePrimitive(ctx, instance, sourceClass);
        }

        // Procesamiento de coleccion de objetos.
        if (Reflection.isInstanceOf(sourceClass, Collection.class)) {
            return resolveCollection(ctx, instance, sourceClass, field);
        }

        // Procesamiento de array de objetos.
        if (sourceClass.isArray()) {
            sourceClass = sourceClass.getComponentType();
            return resolveArray(ctx, instance, sourceClass, field);
        }

        // Procesamiento de objeto.
        return resolveObject(ctx, instance, sourceClass);
    }

    private static JsonElement resolvePrimitive(final SerializationContext ctx, Object instance, Class<?> sourceClass) throws Throwable {
        return ctx.gson.toJsonTree(instance, sourceClass);
    }

    private static JsonElement resolveCollection(final SerializationContext ctx, Object instance, Class<?> sourceClass, Field field) throws Throwable {
        if (!Reflection.isCollection(sourceClass)) {
            throw Errors.illegalArgument("La clase '%s' debe ser una colección.", sourceClass.getCanonicalName());
        }

        if (field == null) {
            throw Errors.illegalArgument("Se esperaba una definición de atributo para resolver la coleccion de objetos.");
        }

        final JsonArrayBuilder builder = JsonArrayBuilder.instance();
        for (Object element : ((Collection) instance)) {
            JsonElement json = resolveInstance(ctx, element, field);
            builder.add(json);
        }

        return builder.build();
    }

    private static JsonElement resolveArray(final SerializationContext ctx, Object instance, Class<?> sourceClass, Field field) throws Throwable {
        if (field == null) {
            throw Errors.illegalArgument("Se esperaba una definición de atributo para resolver el array de objetos.");
        }

        final JsonArrayBuilder builder = JsonArrayBuilder.instance();
        for (Object element : ((Object[]) instance)) {
            JsonElement json = resolveInstance(ctx, element, field);
            builder.add(json);
        }

        return builder.build();
    }

    private static JsonElement resolveObject(final SerializationContext ctx, Object instance, Class<?> sourceClass) throws Throwable {
        JsonObjectBuilder builder = JsonObjectBuilder.instance();

        // Procesar atributos anotados como propiedades.
        Collection<Field> declaredFields = Reflection.getAllFields(sourceClass);
        for (Field field : declaredFields) {
            JsonProperty annotation = Reflection.getAnnotation(field, JsonProperty.class);
            if (annotation != null) {
                // Nombre de propiedad JSON.
                String name = Text.select(annotation.name(), field.getName());

                // Valor de atributo.
                field.setAccessible(true);
                Object fieldValue = field.get(instance);

                // Valor de atributo como JSON.
                JsonElement fieldValueJson = resolveObjectProperty(ctx, fieldValue, field, annotation);

                // Agregar elemento.
                builder.add(name, fieldValueJson);
            }
        }

        return builder.build();
    }

    private static JsonElement resolveObjectProperty(final SerializationContext ctx, Object instance, Field field, JsonProperty annotation) {
        String propertyName = Text.select(annotation.name(), field.getName());

        try {
            // Utilizar adaptador, si hubiere. Los adaptadores tienen preferencia.
            JsonAdapterToJson<Object> adapter = ctx.getAdapter(annotation.toJsonAdapter());
            if (adapter != null) {
                return adapter.adapt(instance);
            } else {
                return resolveInstance(ctx, instance, field);
            }
        } catch (Throwable thr) {
            throw Errors.illegalArgument(thr, "No es posible resolver la propiedad '%s' para la clase '%s'.", propertyName, instance.getClass().getCanonicalName());
        }
    }

    private static class SerializationContext {

        final Gson gson;
        final Map<Class, JsonAdapterToJson> adapters;

        private SerializationContext() {
            this.gson = new Gson();
            this.adapters = Maps.empty();
        }

        private JsonAdapterToJson getAdapter(Class<? extends JsonAdapterToJson> adapterClass) throws Throwable {
            // Control de seguridad.
            if (adapterClass == null || adapterClass.equals(JsonAdapterToJson.class)) {
                return null;
            }

            if (!adapters.containsKey(adapterClass)) {
                JsonAdapterToJson converter = Reflection.tryCreateInstance(adapterClass);
                adapters.put(adapterClass, converter);
            }

            return adapters.get(adapterClass);
        }
    }

}
