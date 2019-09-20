/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.text;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Type;
import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.json.Json;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Fabio A. González Sosa
 */
public class StringTyper {

    public static <T> T typeCast(String string, Type type, T fallback) {
        try {
            Object obj;
            if (Assertions.stringNullOrEmpty(string)) {
                obj = null;
            } else {
                // Obtener objeto en elemento json en base a enumeracion de tipo indicado.
                switch (type) {
                    case JARRAY:
                    case NUMBER:
                        throw new UnsupportedOperationException();
                    case JELEMENT:
                        obj = Json.parse(string);
                        break;
                    case JOBJECT:
                        obj = Json.parse(string).getAsJsonObject();
                        break;
                    case BOOLEAN:
                        obj = Boolean.parseBoolean(string);
                        break;
                    case STRING:
                        obj = string;
                        break;
                    case DOUBLE:
                        obj = Double.parseDouble(string);
                        break;
                    case FLOAT:
                        obj = Float.parseFloat(string);
                        break;
                    case LONG:
                        obj = Long.parseLong(string);
                        break;
                    case INTEGER:
                        obj = Integer.parseInt(string);
                        break;
                    case SHORT:
                        obj = Short.parseShort(string);
                        break;
                    case BYTE:
                        obj = Byte.parseByte(string);
                        break;
                    case CHAR:
                        obj = string.charAt(0);
                        break;
                    case BIGDECIMAL:
                        obj = new BigDecimal(string);
                        break;
                    case BIGINTEGER:
                        obj = new BigInteger(string);
                        break;
                    case EPOCH:
                        long epoch = Long.parseLong(string);
                        obj = Fechas.fromEpoch(epoch);
                        break;
                    default:
                        throw new Exception("Tipo de valor no conocido");
                }
            }

            // Determinar si el valor es utilizable.
            if (obj != null) {
                return (T) obj;
            } else {
                return fallback;
            }
        } catch (Throwable thr) {
            return fallback;
        }
    }

}
