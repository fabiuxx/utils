/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.text.Joiner;
import java.util.Arrays;
import java.util.UUID;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Ids {

    /**
     * Genera una cadena de texto, idealmente unica, a utilizar como
     * identificador.
     *
     * @return Identificador.
     */
    public static String id() {
        return id("");
    }

    /**
     * Genera una cadena de texto, idealmente unica, a utilizar como
     * identificador.
     *
     * @param prefix Prefijo opcional a incluir como parte del identificador.
     * @return Identificador.
     */
    public static String id(String prefix) {
        String uuid = randomUuid();
        if (Assertions.stringNullOrEmpty(prefix)) {
            return uuid;
        } else {
            return String.format("%s__%s", prefix, uuid);
        }
    }

    /**
     * Genera una cadena de texto con la cantidad de segundos, transcurridos
     * desde epoch, a utilizar como identificador.
     *
     * @return Identificador.
     */
    public static String timestampId() {
        long seconds = Fechas.epoch() / 1000L;
        return String.valueOf(seconds);
    }

    /**
     * Genera un UUID aleatorio.
     *
     * @return UUID como cadena sin los separadores (guiones medios).
     */
    public static String randomUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    /**
     * Genera una cadena única que puede ser utilizado en registros que al ser
     * eliminados puedan permitir a nuevos registros re-utilizar sus valores
     * para campos con restriccion UNIQUE.
     *
     * @return Cadena unica.
     */
    public static String generarCadenaEliminacion() {
        String cadena = String.format("$eliminado-%s", Ids.randomUuid());
        return cadena;
    }

    /**
     * Genera un identificador uniforme en base a una coleccion de
     * identificadores.
     *
     * @param ids Coleccion de identificadores.
     * @return Cadena que representa de manera unica a esta coleccion de
     * identificadores.
     */
    public static String join(Integer... ids) {
        Arrays.sort(ids);
        return Joiner.of(ids).separator("_").join();
    }

}
