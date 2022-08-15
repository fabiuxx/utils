/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.Codificables;
import fa.gs.utils.misc.Type;
import fa.gs.utils.misc.Units;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.numeric.Numeric;
import fa.gs.utils.misc.text.Strings;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

/**
 * @author Fabio A. González Sosa
 */
public class JsonResolver {

    /**
     * Obtiene el valor de una propiedad especifica, si hubiere, y lo convierte
     * a un tipo concreto. En caso de no poder encontrar un valor, se genera una
     * excepcion.
     *
     * @param <T> Tipo concreto esperado.
     * @param json Objecto json.
     * @param path Camino hasta la propiedad deseada.
     * @param type Enumeracion de tipo a intentar convertir.
     * @return Valor de propiedad, si hubiere.
     * @throws IllegalArgumentException Si el {@code path} no representa una
     * propiedad valida o si el valor encontrado no puede convertirse al tipo
     * especificado por {@code type}.
     */
    public static <T> T get(JsonObject json, String path, Type type) {
        JsonElement resolved = Json.resolvePath(json, path);
        if (resolved == null) {
            throw Errors.illegalArgument("No se puede resolver el camino '%s'", path);
        }
        try {
            T value = reduceValue(resolved, type, false, null);
            return value;
        } catch (Throwable thr) {
            throw Errors.illegalArgument(thr, "No se puede resolver el camino '%s'", path);
        }
    }

    /**
     * Obtiene el valor de una propiedad especifica, si hubiere, y lo convierte
     * a un tipo concreto. En caso de no poder encontrar un valor, se utiliza un
     * valor alternativo.
     *
     * @param <T> Tipo concreto esperado.
     * @param json Objecto json.
     * @param path Camino hasta la propiedad deseada.
     * @param type Enumeracion de tipo a intentar convertir.
     * @return Valor de propiedad.
     */
    public static <T> T opt(JsonObject json, String path, Type type) {
        return opt(json, path, type, null);
    }

    /**
     * Obtiene el valor de una propiedad especifica, si hubiere, y lo convierte
     * a un tipo concreto. En caso de no poder encontrar un valor, se utiliza un
     * valor alternativo.
     *
     * @param <T> Tipo concreto esperado.
     * @param json Objecto json.
     * @param path Camino hasta la propiedad deseada.
     * @param type Enumeracion de tipo a intentar convertir.
     * @param fallback Valor alternativo a utilizar en caso de no poder procesar
     * el valor de la propiedad.
     * @return Valor de propiedad.
     */
    public static <T> T opt(JsonObject json, String path, Type type, T fallback) {
        try {
            JsonElement resolved = Json.resolvePath(json, path);
            if (resolved == null) {
                return fallback;
            }

            T value = reduceValue(resolved, type, true, fallback);
            return value;
        } catch (Throwable thr) {
            return fallback;
        }
    }

    /**
     * Convierte el valor de una propiedad dentro de un elemento JSON a un tipo
     * concreto.
     *
     * @param <T> Tipo concreto esperado.
     * @param json Elemento json.
     * @param type Enumeracion de tipo a intentar convertir.
     * @return Valor de la propiedad.
     * @throws Exception Si no se puede procesar el valor del elemento json.
     */
    public static <T> T reduceValue(JsonElement json, Type type) throws Exception {
        return reduceValue(json, type, false, null);
    }

    /**
     * Convierte el valor de una propiedad dentro de un elemento JSON a un tipo
     * concreto.
     *
     * @param <T> Tipo concreto esperado.
     * @param json Elemento json.
     * @param type Enumeracion de tipo a intentar convertir.
     * @param useFallback Indica si se puede utilizar un valor alternativo.
     * @param fallback Valor alternativo en caso de que no se pueda utilizar el
     * valor encontrado en la propiedad.
     * @return Valor de la propiedad.
     * @throws Exception Si no se puede procesar el valor del elemento json.
     */
    public static <T> T reduceValue(JsonElement json, Type type, boolean useFallback, T fallback) throws Exception {
        try {
            Object obj;
            if (json.isJsonNull()) {
                obj = null;
            } else {
                // Obtener objeto en elemento json en base a enumeracion de tipo indicado.
                switch (type) {
                    case JARRAY:
                        obj = json.getAsJsonArray();
                        break;
                    case JOBJECT:
                        obj = json.getAsJsonObject();
                        break;
                    case BOOLEAN:
                        obj = json.getAsBoolean();
                        break;
                    case NUMBER:
                        obj = json.getAsNumber();
                        break;
                    case STRING:
                        obj = json.getAsString();
                        break;
                    case DOUBLE:
                        obj = json.getAsDouble();
                        break;
                    case FLOAT:
                        obj = json.getAsFloat();
                        break;
                    case LONG:
                        obj = json.getAsLong();
                        break;
                    case INTEGER:
                        obj = json.getAsInt();
                        break;
                    case SHORT:
                        obj = json.getAsShort();
                        break;
                    case BYTE:
                        obj = json.getAsByte();
                        break;
                    case CHAR:
                        obj = json.getAsCharacter();
                        break;
                    case BIGDECIMAL:
                        obj = json.getAsBigDecimal();
                        break;
                    case BIGINTEGER:
                        obj = json.getAsBigInteger();
                        break;
                    case EPOCH:
                        long epoch = json.getAsLong();
                        obj = Fechas.fromEpoch(epoch);
                        break;
                    default:
                        throw new Exception("Tipo de valor no conocido");
                }
            }

            // Determinar si el valor es utilizable.
            if (obj != null) {
                return (T) obj;
            } else if (obj == null && !useFallback) {
                throw new Exception("Valor no definido");
            } else if (obj == null && useFallback) {
                return fallback;
            } else {
                throw new Exception("No es posible realizar la operacion");
            }
        } catch (Throwable thr) {
            String msg = Strings.format("No se puede reducir el valor del elemento JSON (%s) al tipo '%s'", json.getClass().getSimpleName(), type.toString());
            throw new Exception(msg, thr);
        }
    }

    public static JsonArray jArray(JsonObject json, String path) {
        return jArray(json, path, null);
    }

    public static JsonArray jArray(JsonObject json, String path, JsonArray fallback) {
        Object value = JsonResolver.opt(json, path, Type.JARRAY, fallback);
        return JsonArray.class.cast(value);
    }

    public static Collection<JsonArray> jArrayCollection(JsonObject json, String path) {
        return collection(json, path, Type.JARRAY, JsonArray.class);
    }

    public static JsonObject jObject(JsonObject json, String path) {
        return jObject(json, path, null);
    }

    public static JsonObject jObject(JsonObject json, String path, JsonObject fallback) {
        Object value = JsonResolver.opt(json, path, Type.JOBJECT, fallback);
        return JsonObject.class.cast(value);
    }

    public static Collection<JsonObject> jObjectCollection(JsonObject json, String path) {
        return collection(json, path, Type.JOBJECT, JsonObject.class);
    }

    public static Boolean bool(JsonObject json, String path) {
        return bool(json, path, null);
    }

    public static Boolean bool(JsonObject json, String path, Boolean fallback) {
        Object value = JsonResolver.opt(json, path, Type.BOOLEAN, fallback);
        return Boolean.class.cast(value);
    }

    public static Collection<Boolean> boolCollection(JsonObject json, String path) {
        return collection(json, path, Type.BOOLEAN, Boolean.class);
    }

    public static String string(JsonObject json, String path) {
        return string(json, path, null);
    }

    public static String string(JsonObject json, String path, String fallback) {
        Object value = JsonResolver.opt(json, path, Type.STRING, fallback);
        return String.class.cast(value);
    }

    public static Collection<String> stringCollection(JsonObject json, String path) {
        return collection(json, path, Type.STRING, String.class);
    }

    public static Integer integer(JsonObject json, String path) {
        return integer(json, path, null);
    }

    public static Integer integer(JsonObject json, String path, Integer fallback) {
        Object value = JsonResolver.opt(json, path, Type.INTEGER, fallback);
        return Integer.class.cast(value);
    }

    public static Long long0(JsonObject json, String path) {
        return long0(json, path, null);
    }

    public static Long long0(JsonObject json, String path, Long fallback) {
        Object value = JsonResolver.opt(json, path, Type.LONG, fallback);
        return Numeric.adaptAsLong(value);
    }

    public static Double double0(JsonObject json, String path) {
        return double0(json, path, null);
    }

    public static Double double0(JsonObject json, String path, Double fallback) {
        Object value = JsonResolver.opt(json, path, Type.DOUBLE, fallback);
        return Double.class.cast(value);
    }

    public static Collection<Integer> integerCollection(JsonObject json, String path) {
        return collection(json, path, Type.INTEGER, Integer.class);
    }

    public static BigInteger biginteger(JsonObject json, String path) {
        return biginteger(json, path, null);
    }

    public static BigInteger biginteger(JsonObject json, String path, BigInteger fallback) {
        Object value = JsonResolver.opt(json, path, Type.BIGINTEGER, fallback);
        return Numeric.adaptAsBigInteger(value);
    }

    public static Collection<BigInteger> bigintegerCollection(JsonObject json, String path) {
        return collection(json, path, Type.BIGINTEGER, BigInteger.class);
    }

    public static BigDecimal bigdecimal(JsonObject json, String path) {
        return bigdecimal(json, path, null);
    }

    public static BigDecimal bigdecimal(JsonObject json, String path, BigDecimal fallback) {
        Object value = JsonResolver.opt(json, path, Type.BIGDECIMAL, fallback);
        return Numeric.adaptAsBigDecimal(value);
    }

    public static Collection<BigDecimal> bigdecimalCollection(JsonObject json, String path) {
        return collection(json, path, Type.BIGDECIMAL, BigDecimal.class);
    }

    public static Date date(JsonObject json, String path) {
        return date(json, path, null);
    }

    public static Date date(JsonObject json, String path, Date fallback) {
        Object value = JsonResolver.opt(json, path, Type.EPOCH, fallback);
        return Date.class.cast(value);
    }

    public static Collection<Date> dateCollection(JsonObject json, String path) {
        return collection(json, path, Type.EPOCH, Date.class);
    }

    private static <T> Collection<T> collection(JsonObject json, String path, Type type, Class<T> klass) {
        Collection<T> values = Lists.empty();
        JsonArray array = jArray(json, path);
        for (JsonElement element : array) {
            Object value0 = Units.execute(null, () -> reduceValue(element, type));
            T value = klass.cast(value0);
            values.add(value);
        }
        return values;
    }

    public static <T extends Codificable> T codificable(JsonObject json, String path, Class<T> klass) {
        String codigo = JsonResolver.string(json, path);
        if (klass.isEnum()) {
            for (T value : klass.getEnumConstants()) {
                if (Assertions.equals(value.codigo(), codigo)) {
                    return value;
                }
            }
        }
        return null;
    }

    public static <T extends Codificable> T codificable(JsonObject json, String path, T[] values) {
        String codigo = JsonResolver.string(json, path);
        return Codificables.fromCodigo(codigo, values);
    }

    public static <T> T object(JsonObject json, String path, JsonResolver.Mapper<T> mapper) {
        JsonElement element = Json.resolvePath(json, path);
        if (element == null) {
            return null;
        } else {
            return mapper.map(element);
        }
    }

    public static <T> Collection<T> objectCollection(JsonObject json, String path, JsonResolver.Mapper<T> mapper) {
        Collection<T> instances = Lists.empty();

        JsonElement element = Json.resolvePath(json, path);
        if (element != null && element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (JsonElement arrayElement : array) {
                T instance = mapper.map(arrayElement);
                instances.add(instance);
            }
        }

        return instances;
    }

    /**
     * Interface generica para conversion simple de elementos JSON a objetos.
     * Existe principalmente para que se pueda simplificar el codigo en entornos
     * android mediante funciones lambda.
     *
     * @param <T> Parametro de tipo.
     */
    @FunctionalInterface
    public static interface Mapper<T> {

        /**
         * Convierte una elemento JSON a un objeto Java.
         *
         * @param json Elemento JSON.
         * @return
         */
        public T map(JsonElement json);

    }

}
