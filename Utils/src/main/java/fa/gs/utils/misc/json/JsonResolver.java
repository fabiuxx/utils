/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fa.gs.utils.misc.Type;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.fechas.Fechas;

/**
 * @deprecated Utilizar funcionalidades de paquete
 * {@link fa.gs.utils.misc.json.serialization}.
 * @author Fabio A. González Sosa
 */
@Deprecated
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
     * @throws IllegalArgumentException Si el <code>path</code> no representa
     * una propiedad valida o si el valor encontrado no puede convertirse al
     * tipo especificado por <code>type</code>.
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
            return get(json, path, type);
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
            throw new Exception(String.format("No se puede reducir el valor del elemento JSON (%s) al tipo '%s'", json.getClass().getSimpleName(), type.toString()), thr);
        }
    }

}
