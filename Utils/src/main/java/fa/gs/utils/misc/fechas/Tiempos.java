/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.fechas;

import java.util.concurrent.TimeUnit;

/**
 * Clase auxiliar que define valores constantes para algunos lapsos de tiempo,
 * expresados en milisegundos.
 *
 * @author Fabio A. González Sosa
 */
public class Tiempos {

    public static final long MILLIS_01_SECONDS = TimeUnit.SECONDS.toMillis(1);
    public static final long MILLIS_05_SECONDS = TimeUnit.SECONDS.toMillis(5);
    public static final long MILLIS_10_SECONDS = TimeUnit.SECONDS.toMillis(10);
    public static final long MILLIS_15_SECONDS = TimeUnit.SECONDS.toMillis(15);
    public static final long MILLIS_20_SECONDS = TimeUnit.SECONDS.toMillis(20);
    public static final long MILLIS_25_SECONDS = TimeUnit.SECONDS.toMillis(25);
    public static final long MILLIS_30_SECONDS = TimeUnit.SECONDS.toMillis(30);
    public static final long MILLIS_45_SECONDS = TimeUnit.SECONDS.toMillis(45);
    public static final long MILLIS_60_SECONDS = TimeUnit.SECONDS.toMillis(60);
    public static final long MILLIS_05_MINUTES = TimeUnit.MINUTES.toMillis(5);
    public static final long MILLIS_10_MINUTES = TimeUnit.MINUTES.toMillis(10);
    public static final long MILLIS_15_MINUTES = TimeUnit.MINUTES.toMillis(15);
    public static final long MILLIS_20_MINUTES = TimeUnit.MINUTES.toMillis(20);
    public static final long MILLIS_25_MINUTES = TimeUnit.MINUTES.toMillis(25);
    public static final long MILLIS_30_MINUTES = TimeUnit.MINUTES.toMillis(30);
    public static final long MILLIS_45_MINUTES = TimeUnit.MINUTES.toMillis(45);
    public static final long MILLIS_60_MINUTES = TimeUnit.MINUTES.toMillis(60);

    /**
     * Obtiene la cantidad de milisegundos que componen una cantidad arbitraria
     * de segundos.
     *
     * @param n Cantidad de segundos a convertir a milisegundos.
     * @return Cantidad de milisegundos.
     */
    public static long SECONDS(int n) {
        return TimeUnit.SECONDS.toMillis(n);
    }

    /**
     * Obtiene la cantidad de milisegundos que componen una cantidad arbitraria
     * de minutos.
     *
     * @param n Cantidad de minutos a convertir a milisegundos.
     * @return Cantidad de milisegundos.
     */
    public static long MINUTES(int n) {
        return TimeUnit.MINUTES.toMillis(n);
    }

    /**
     * Obtiene la cantidad de milisegundos que componen una cantidad arbitraria
     * de horas.
     *
     * @param n Cantidad de horas a convertir a milisegundos.
     * @return Cantidad de milisegundos.
     */
    public static long HOURS(int n) {
        return TimeUnit.HOURS.toMillis(n);
    }

}
