/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.fechas;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Locales;
import fa.gs.utils.misc.text.Strings;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Fechas {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Nombres de los 12 meses del año.
     */
    public static final String[] meses = {
        "Enero",
        "Febrero",
        "Marzo",
        "Abril",
        "Mayo",
        "Junio",
        "Julio",
        "Agosto",
        "Septiembre",
        "Octubre",
        "Noviembre",
        "Diciembre"
    };

    private static final String[] eraNames = {"DC", "AC"};

    private static final String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    private static final String[] monthNamesShort = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

    private static final String[] dayNames = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

    public static final Dia[] dias = {Dia.DOMINGO, Dia.LUNES, Dia.MARTES, Dia.MIERCOLES, Dia.JUEVES, Dia.VIERNES, Dia.SABADO};

    private static final String[] dayNamesShort = {"Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};

    /**
     * Dias en un mes.
     */
    public static final int[] dias_mes = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
        11, 12, 13, 14, 15, 16, 17, 18,
        19, 20, 21, 22, 23, 24, 25, 26,
        27, 28, 29, 30, 31
    };

    /**
     * Diez anhos incluyendo el actual.
     */
    public static final int[] anhos = Fechas.diezAnhos();
    //</editor-fold>

    /**
     * Obtiene un objeto fecha con el valor actual.
     *
     * @return Fecha actual.
     */
    public static Date now() {
        Calendar calendar = Calendar.getInstance(Locales.es_ES);
        return calendar.getTime();
    }

    /**
     * <p>
     * Obtiene el "offset" en milisegundos de la zona horaria actual respecto a
     * UTC-0.
     * </p>
     * <p>
     * Ej.: Para Paraguay, considerando UTC-4 el offset corresponderia a -0400,
     * es decir -14400000 milisegundos (-4 horas).
     * </p>
     *
     * @return "offset" en milisegundos de la zona horaria local.
     */
    public static long timeZoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cl = Calendar.getInstance(Locales.es_ES);
        return tz.getOffset(cl.getTimeInMillis());
    }

    /**
     * Unifica los simbolos de meses y dias de la semana acorde al locale 'es'.
     *
     * @return Formato de simbolos para meses y dias de la semana.
     */
    private static DateFormatSymbols getLocalizedDateFormatSymbols() {
        DateFormatSymbols dfs = new DateFormatSymbols(Locales.es_ES);
        dfs.setEras(eraNames);
        dfs.setMonths(monthNames);
        dfs.setShortMonths(monthNamesShort);
        dfs.setWeekdays(dayNames);
        dfs.setShortWeekdays(dayNamesShort);
        return dfs;
    }

    /**
     * Obtiene el formateador por defecto de fechas, para el locale 'es'.
     *
     * @param pattern Patron de formateo.
     * @return Formateador de fechas.
     */
    private static DateFormat getLocalizedDateFormat(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locales.es_ES);
        sdf.setDateFormatSymbols(getLocalizedDateFormatSymbols());
        return sdf;
    }

    /**
     * Obtiene un objeto de tipo Date a partir de una representacion de texto.
     *
     * @param text Representacion de texto.
     * @param format Formato de la fecha a parsear.
     * @return Objeto Date.
     */
    public static Date parse(String text, String format) {
        try {
            DateFormat sdf = getLocalizedDateFormat(format);
            return sdf.parse(text);
        } catch (Throwable thr) {
            return null;
        }
    }

    /**
     * Obtiene una referencia del dia correspondiente a la fecha indicada.
     *
     * @param input Fecha de referencia.
     * @return Dia de la semana.
     */
    public static Dia getDia(Date input) {
        Calendar calendar = Calendar.getInstance(Locales.es_ES);
        calendar.setTime(input);
        int dia = calendar.get(Calendar.DAY_OF_WEEK);
        return dias[dia - 1];
    }

    /**
     * Obtiene el numero de semana en el mes correspondiente a la fecha
     * indicada.
     *
     * @param input Fecha de referencia.
     * @return Nro de semana.
     */
    public static int getNroSemana(Date input) {
        Calendar c = Calendar.getInstance(Locales.es_ES);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(input);

        int min = c.getActualMinimum(Calendar.WEEK_OF_MONTH);
        int diff = (min == 0) ? 1 : 0;
        return c.get(Calendar.WEEK_OF_MONTH) + diff;
    }

    /**
     * Obtiene una referencia del anho actual.
     *
     * @return Anho corriente.
     */
    public static int getAnho() {
        Date now = Fechas.now();
        return now.getYear() + 1900;
    }

    /**
     * Obtiene el nombre del mes correspondiente al entero entre 1 y 12.
     *
     * @param m Entero entre 1 y 12 que representa numericamente al mes.
     * @return Nombre del mes.
     */
    public static String getNombreMes(Integer m) {
        if (m < 1 || m > 12) {
            throw Errors.illegalArgument("Se esperaba un valor entre 1 y 12");
        }
        return meses[m - 1];
    }

    public static Collection<DateInfo> obtenerInformacionMes(Date fecha) {
        Date inicio = primerDiaMes(fecha);
        Date fin = ultimoDiaMes(fecha);
        return obtenerInformacionFechas(inicio, fin);
    }

    public static Collection<DateInfo> obtenerInformacionFechas(Date inicio, Date fin) {
        Collection<DateInfo> infos = Lists.empty();
        Date dia = inicio;
        while (true) {
            // Procesar dia.
            DateInfo info = new DateInfo();
            info.setFecha(dia);
            info.setDia(getDia(dia));
            info.setNroSemana(getNroSemana(dia));
            infos.add(info);

            // Avanzar a siguiente dia.
            dia = addDias(dia, 1);
            if (dia.compareTo(fin) > 0) {
                break;
            }
        }
        return infos;
    }

    /**
     * Genera un array con los siguientes diez anhos incluyendo el actual.
     *
     * @return Array de anhos.
     */
    public static final int[] diezAnhos() {
        int[] anhos = new int[10];
        int year = Calendar.getInstance(Locales.es_ES).get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            anhos[i] = year + i;
        }
        return anhos;
    }

    /**
     * Combina dos objetos de tipo Date para formar uno solo. Este metodo es
     * complementario a separarFechaHora.
     *
     * @param fecha Objeto Date donde nos interesa solo la parte de fecha
     * (dd/mm/yy).
     * @param hora Object Date donde nos interesa solo la parte de tiempo
     * (hh:mm).
     * @return Objeto Date que combina las partes de fecha y tiempo (dd/mm/yy
     * hh:mm).
     */
    public static Date combinarFechaHora(Date fecha, Date hora) {
        Calendar cFecha = Calendar.getInstance(Locales.es_ES);
        cFecha.setTime(fecha);

        Calendar cHora = Calendar.getInstance(Locales.es_ES);
        cHora.setTime(hora);

        Calendar cFechaHora = Calendar.getInstance(Locales.es_ES);
        cFechaHora.set(Calendar.ERA, GregorianCalendar.AD);
        cFechaHora.set(Calendar.YEAR, cFecha.get(Calendar.YEAR));
        cFechaHora.set(Calendar.MONTH, cFecha.get(Calendar.MONTH));
        cFechaHora.set(Calendar.DAY_OF_MONTH, cFecha.get(Calendar.DAY_OF_MONTH));
        cFechaHora.set(Calendar.HOUR_OF_DAY, cHora.get(Calendar.HOUR_OF_DAY));
        cFechaHora.set(Calendar.MINUTE, cHora.get(Calendar.MINUTE));
        cFechaHora.set(Calendar.SECOND, cHora.get(Calendar.SECOND));
        cFechaHora.set(Calendar.MILLISECOND, cHora.get(Calendar.MILLISECOND));
        return cFechaHora.getTime();
    }

    /**
     * Separa un objeto Date en dos diferentes separados por las parte de fecha
     * y tiempo. Este metodo es complementario a combinarFechaHora.
     *
     * @param fecha Objeto Date combinado que contiene las partes de fecha y
     * tiempo (dd/mm/yy hh:mm).
     * @return Array de dos objetos Date donde el primer elemento corresponde a
     * la parte de fecha (dd/mm/yy) y el segundo elemento corresponde a la parte
     * de tiempo (hh:mm).
     */
    public static Date[] separarFechaHora(Date fecha) {
        Calendar cFechaHora = Calendar.getInstance(Locales.es_ES);
        cFechaHora.setTime(fecha);

        Calendar cFecha = Calendar.getInstance(Locales.es_ES);
        cFecha.set(Calendar.ERA, GregorianCalendar.AD);
        cFecha.set(Calendar.YEAR, cFechaHora.get(Calendar.YEAR));
        cFecha.set(Calendar.MONTH, cFechaHora.get(Calendar.MONTH));
        cFecha.set(Calendar.DAY_OF_MONTH, cFechaHora.get(Calendar.DAY_OF_MONTH));

        Calendar cHora = Calendar.getInstance(Locales.es_ES);
        cHora.set(Calendar.HOUR_OF_DAY, cFechaHora.get(Calendar.HOUR_OF_DAY));
        cHora.set(Calendar.MINUTE, cFechaHora.get(Calendar.MINUTE));
        cHora.set(Calendar.SECOND, cFechaHora.get(Calendar.SECOND));
        cHora.set(Calendar.MILLISECOND, cFechaHora.get(Calendar.MILLISECOND));

        Date[] fechaHora = new Date[]{cFecha.getTime(), cHora.getTime()};
        return fechaHora;
    }

    /**
     * Toma un objeto fecha como referencia y modifica el mismo para establecer
     * los valores de dia, mes y anho indicados.
     *
     * @param input Fecha de referencia.
     * @param dia Dia (0 - 31)
     * @param mes Mes (1 - 12)
     * @param anho Anho.
     * @return Fecha con valores establecidos.
     */
    public static Date setFecha(Date input, int dia, int mes, int anho) {
        if (input == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance(Locales.es_ES);
        cal.setTime(input);
        cal.set(Calendar.ERA, GregorianCalendar.AD);
        cal.set(Calendar.YEAR, anho);
        cal.set(Calendar.MONTH, mes - 1);
        cal.set(Calendar.DAY_OF_MONTH, dia);
        return cal.getTime();
    }

    /**
     * Toma un objeto fecha como referencia y modifica el mismo para establecer
     * los valores de hora, minuto y segundos indicados.
     *
     * @param input Fecha de referencia.
     * @param hora Hora (0-23).
     * @param minuto Minuto (0-59).
     * @param segundo Segundo (0-59).
     * @return Fecha con valores establecidos.
     */
    public static Date setHora(Date input, int hora, int minuto, int segundo) {
        return setHora(input, hora, minuto, segundo, 0);
    }

    /**
     * Toma un objeto fecha como referencia y modifica el mismo para establecer
     * los valores de hora, minuto y segundos indicados.
     *
     * @param input Fecha de referencia.
     * @param hora Hora (0-23).
     * @param minuto Minuto (0-59).
     * @param segundo Segundo (0-59).
     * @param milisegundo Milisegundo (0-999).
     * @return Fecha con valores establecidos.
     */
    public static Date setHora(Date input, int hora, int minuto, int segundo, int milisegundo) {
        if (input == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance(Locales.es_ES);
        cal.setTime(input);
        cal.set(Calendar.ERA, GregorianCalendar.AD);
        cal.set(Calendar.HOUR_OF_DAY, hora);
        cal.set(Calendar.MINUTE, minuto);
        cal.set(Calendar.SECOND, segundo);
        cal.set(Calendar.MILLISECOND, milisegundo);
        return cal.getTime();
    }

    /**
     * Toma un objeto fecha como referencia y modifica el mismo para
     * agregar/restar un valor indicado al campo indicado.
     *
     * @param input Fecha de referencia.
     * @param field Campo a modificar. Cualquier definido en
     * {@link java.util.Calendar}.
     * @param value Valor a sumar/restar.
     * @return Fecha modificada.
     */
    public static Date addValue(Date input, int field, int value) {
        Calendar calendar = Calendar.getInstance(Locales.es_ES);
        calendar.setTime(input);
        calendar.add(field, value);
        return calendar.getTime();
    }

    /**
     * Agrega una cierta cantidad de dias a la fecha indicada.
     *
     * @param input Fecha de entrada.
     * @param n Cantidad de dias de incremento.
     * @return Nueva fecha.
     */
    public static Date addDias(Date input, int n) {
        return addValue(input, Calendar.DAY_OF_MONTH, n);
    }

    /**
     * Toma un objeto fecha como referencia y obtiene el valor del campo
     * indicado.
     *
     * @param input Fecha de referencia.
     * @param field Cualquier campo definido en {@link java.util.Calendar}.
     * @return Valor del campo.
     */
    public static int getFieldValue(Date input, int field) {
        Calendar calendar = Calendar.getInstance(Locales.es_ES);
        calendar.setTime(input);
        return calendar.get(field);
    }

    /**
     * Toma un objeto fecha como referencia y modifica el campo indicado.
     *
     * @param input Fecha de referencia.
     * @param field Cualquier campo definido en {@link java.util.Calendar}.
     * @param value Valor del campo.
     * @return Objeto fecha modificado.
     */
    public static Date setFieldValue(Date input, int field, int value) {
        Calendar calendar = Calendar.getInstance(Locales.es_ES);
        calendar.setTime(input);
        calendar.set(field, value);
        return calendar.getTime();
    }

    /**
     * Toma un objeto fecha como referencia y calcula el primer dia de ese mes.
     *
     * @param input Fecha de referencia.
     * @return Objeto fecha modificado.
     */
    public static Date primerDiaMes(Date input) {
        Date fecha = Fechas.setFieldValue(input, Calendar.DAY_OF_MONTH, 1);
        return Fechas.setHora(fecha, 0, 0, 0);
    }

    /**
     * Toma un objeto fecha como referencia y calcula el ultimo dia de ese mes.
     *
     * @param input Fecha de referencia.
     * @return Objeto fecha modificado.
     */
    public static Date ultimoDiaMes(Date input) {
        Calendar c = Calendar.getInstance(Locales.es_ES);
        c.setTime(input);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date fecha = c.getTime();
        return Fechas.setHora(fecha, 23, 59, 59);
    }

    /**
     * Toma un objeto fecha como referencia y obtiene la hora de inicio de ese
     * dia que representa.
     *
     * @param input Fecha de referencia.
     * @return Objeto fecha modificado.
     */
    public static Date inicioDia(Date input) {
        return Fechas.setHora(input, 0, 0, 0);
    }

    /**
     * Toma un objeto fecha como referencia y obtiene la hora de fin de ese dia
     * que representa.
     *
     * @param input Fecha de referencia.
     * @return Objeto fecha modificado.
     */
    public static Date finDia(Date input) {
        return Fechas.setHora(input, 23, 59, 59);
    }

    /**
     * Formatea una cantidad arbitraria de segundos y retorna una cadena
     * formateada de tipo d h m s.
     *
     * @param segundos Cantidad de segundos.
     * @return Cadena formateada.
     */
    public static String segundosAsText(Long segundos) {
        // Control de seguridad.
        if (segundos == null || segundos < 0) {
            return "";
        }

        // Procesar datos de tiempo en base a segundos ingresados.
        long d = TimeUnit.SECONDS.toDays(segundos);
        long h = TimeUnit.SECONDS.toHours(segundos) - TimeUnit.DAYS.toHours(d);
        long m = TimeUnit.SECONDS.toMinutes(segundos) - TimeUnit.DAYS.toMinutes(d) - TimeUnit.HOURS.toMinutes(h);
        long s = TimeUnit.SECONDS.toSeconds(segundos) - TimeUnit.DAYS.toSeconds(d) - TimeUnit.HOURS.toSeconds(h) - TimeUnit.MINUTES.toSeconds(m);

        // Conversion a texto.
        StringBuilder builder = new StringBuilder();
        if (d > 0) {
            builder.append(Strings.format("%d d ", d));
        }
        if (h > 0) {
            builder.append(Strings.format("%02d h ", h));
        }
        if (m > 0) {
            builder.append(Strings.format("%02d m ", m));
        }
        if (s > 0) {
            builder.append(Strings.format("%02d s ", s));
        }
        return builder.toString().trim();
    }

    /**
     * Convierte un objeto de tipo date a una cadena utilizando el formato
     * dd/MM/yyyy HH:mm:ss.
     *
     * @param date Objeto fecha.
     * @return Cadena formateada. Cadena vacia si la fecha es nula.
     */
    public static String toString(Date date) {
        return toString(date, Formats.DD_MM_YYYY_HH_MM_SS);
    }

    /**
     * Convierte un objeto de tipo date a una cadena utilizando el formato
     * dd/MM/yyyy HH:mm:ss.
     *
     * @param date Objeto fecha.
     * @param format Formato de fecha.
     * @return Cadena formateada. Cadena vacia si la fecha es nula.
     */
    public static String toString(Date date, String format) {
        if (date == null) {
            return null;
        }
        DateFormat sdf = getLocalizedDateFormat(format);
        String txt = sdf.format(date);
        return txt;
    }

    /**
     * Convierte un objeto de tipo date a una cadena utilizando el formato
     * dd/MM/yyyy HH:mm:ss.
     *
     * @param date Objeto fecha.
     * @param format Formato de fecha.
     * @param timeZone Zona horaria.
     * @return Cadena formateada. Cadena vacia si la fecha es nula.
     */
    public static String toString(Date date, String format, String timeZone) {
        if (date == null) {
            return null;
        }
        DateFormat sdf = getLocalizedDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String txt = sdf.format(date).toUpperCase();
        return txt;
    }

    /**
     * Convierte un objeto de tipo date a una cadena utilizando el formato
     * yyyymmddHHmmss.
     *
     * @param date Objeto fecha.
     * @return Cadena formateada. Cadena vacia si la fecha es nula.
     */
    public static String toCompactString(Date date) {
        return toString(date, Formats.YYYYMMDDHHMMSS);
    }

    /**
     * Obtiene la cantidad de milisegundos que pasaron desde <i>01-ENE-1970
     * 00:00:00</i> (epoch) hasta el momento actual.
     *
     * @return Cantidad de milisegundos.
     */
    public static Long epoch() {
        return toEpoch(Fechas.now());
    }

    /**
     * Convierte un objeto de tipo {@link java.util.Date Date} a un entero que
     * representa la cantidad de milisegundos que pasaron desde <i>01-ENE-1970
     * 00:00:00</i> (epoch).
     *
     * @param date Objecto fecha.
     * @return Cantidad de milisegundos.
     */
    public static Long toEpoch(Date date) {
        if (date == null) {
            return -1L;
        } else {
            return date.getTime();
        }
    }

    /**
     * Convierte un valor de texto que representa la cantidad de milisegundos
     * que pasaron desde 01-ENE-1970 00:00:00 (epoch) a un objeto de tipo
     * {@link java.util.Date Date}.
     *
     * @param epoch Texto numerico.
     * @return Objeto fecha.
     */
    public static Date fromEpoch(String epoch) {
        Long e = Long.valueOf(epoch);
        return fromEpoch(e);
    }

    /**
     * Convierte un valor entero que representa la cantidad de milisegundos que
     * pasaron desde 01-ENE-1970 00:00:00 (epoch) a un objeto de tipo
     * {@link java.util.Date Date}.
     *
     * @param epoch Cantidad de milisegundos.
     * @return Objeto fecha.
     */
    public static Date fromEpoch(Long epoch) {
        if (epoch <= 0) {
            return null;
        } else {
            return new Date(epoch);
        }
    }

    public static boolean menor(Date a, Date b) {
        int compare = a.compareTo(b);
        return compare < 0;
    }

    public static boolean mayor(Date a, Date b) {
        int compare = a.compareTo(b);
        return compare > 0;
    }

    public static boolean igual(Date a, Date b) {
        int compare = a.compareTo(b);
        return compare == 0;
    }

    public static boolean menorIgual(Date a, Date b) {
        int compare = a.compareTo(b);
        return compare <= 0;
    }

    public static boolean mayorIgual(Date a, Date b) {
        int compare = a.compareTo(b);
        return compare >= 0;
    }

    public static enum Dia implements Codificable {
        DOMINGO("0001", "Domingo"),
        LUNES("0002", "Lunes"),
        MARTES("0003", "Martes"),
        MIERCOLES("0004", "Miércoles"),
        JUEVES("0005", "Jueves"),
        VIERNES("0006", "Viernes"),
        SABADO("0007", "Sábado");
        private final String codigo;
        private final String descripcion;

        private Dia(String codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }

        @Override
        public String codigo() {
            return codigo;
        }

        @Override
        public String descripcion() {
            return descripcion;
        }
    }

}
