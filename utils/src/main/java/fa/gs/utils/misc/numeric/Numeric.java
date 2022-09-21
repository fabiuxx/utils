/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.numeric;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.Locales;
import fa.gs.utils.misc.text.Padding;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Numeric {

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    /**
     * Patron para formateo de valores numericos a cadenas de texto.
     * Correspondiente con los valores establecidos para
     * {@code DEFAULT_MAX_INTEGER_DIGITS} y {@code DEFAULT_MAX_FRACTION_DIGITS}.
     */
    public static final String DEFAULT_NUMBER_PATTERN = "###,###,###,###,###,###,###,###,###,###.######";

    /**
     * Cantidad maxima de digitos en la parte entera de un numero real.
     */
    public static final int DEFAULT_MAX_INTEGER_DIGITS = 30;

    /**
     * Cantidad maxima de digitos en la parte decimal de un numero real.
     */
    public static final int DEFAULT_MAX_FRACTION_DIGITS = 6;

    /**
     * Constante que representa al valor 0.
     */
    public static final BigDecimal CERO = BigDecimal.ZERO;

    /**
     * Constante que representa al valor 1.
     */
    public static final BigDecimal UNO = BigDecimal.ONE;

    /**
     * Constante que representa al valor -1.
     */
    public static final BigDecimal UNO_NEGATIVO = BigDecimal.ONE.negate();

    /**
     * Constante que representa al valor 10.
     */
    public static final BigDecimal DIEZ = BigDecimal.TEN;

    /**
     * Constante que representa al valor 100.
     */
    public static final BigDecimal CIEN = BigDecimal.valueOf(100.0);

    /**
     * Constante que representa al valor 1000.
     */
    public static final BigDecimal MIL = BigDecimal.valueOf(1000.0);

    /**
     * Tipo de redondeo por defecto.
     */
    public static final RoundingMode DEFAULT_ROUNDING_MODE = Redondeo.MITAD_AL_PAR;
    //</editor-fold>

    /**
     * Determina si el valor representado puede considerarse como un entero. Es
     * decir, si los decimales que contiene son todos iguales a 0.
     *
     * @param value Valor numerico.
     * @return Si el valor contiene decimales distintos de cero.
     */
    public static boolean puedeConsiderarseEntero(BigDecimal value) {
        https://stackoverflow.com/a/12748321
        return value.signum() == 0 || value.scale() <= 0 || value.stripTrailingZeros().scale() <= 0;
    }

    /**
     * Adapta un valor numerico de tipo indeterminado a un valor concreto de
     * tipo {@link java.lang.Integer Integer}.
     *
     * @param obj Valor numerico arbitratio.
     * @return Valor numerico adaptado.
     */
    public static Integer adaptAsInteger(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Short) {
            return ((Short) obj).intValue();
        }

        if (obj instanceof Integer) {
            return (Integer) obj;
        }

        if (obj instanceof Long) {
            return ((Long) obj).intValue();
        }

        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }

        if (obj instanceof Double) {
            return ((Double) obj).intValue();
        }

        if (obj instanceof BigInteger) {
            return ((BigInteger) obj).intValue();
        }

        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValue();
        }

        if (obj instanceof String) {
            return Integer.valueOf((String) obj);
        }

        throw Errors.illegalArgument("No se puede convertir '%s' a Integer.", obj.getClass().getCanonicalName());
    }

    /**
     * Adapta un valor numerico de tipo indeterminado a un valor concreto de
     * tipo {@link java.lang.Long Long}.
     *
     * @param obj Valor numerico arbitratio.
     * @return Valor numerico adaptado.
     */
    public static Long adaptAsLong(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Short) {
            return ((Short) obj).longValue();
        }

        if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }

        if (obj instanceof Long) {
            return (Long) obj;
        }

        if (obj instanceof Float) {
            return ((Float) obj).longValue();
        }

        if (obj instanceof Double) {
            return ((Double) obj).longValue();
        }

        if (obj instanceof BigInteger) {
            return ((BigInteger) obj).longValue();
        }

        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).longValue();
        }

        if (obj instanceof String) {
            return Long.valueOf((String) obj);
        }

        throw Errors.illegalArgument("No se puede convertir '%s' a Long.", obj.getClass().getCanonicalName());
    }

    /**
     * Adapta un valor numerico de tipo indeterminado a un valor concreto de
     * tipo {@link java.math.BigInteger BigInteger}.
     *
     * @param obj Valor numerico arbitratio.
     * @return Valor numerico adaptado.
     */
    public static BigInteger adaptAsBigInteger(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Short) {
            String val = String.valueOf(obj);
            return new BigInteger(val, 10);
        }

        if (obj instanceof Integer) {
            String val = String.valueOf(obj);
            return new BigInteger(val, 10);
        }

        if (obj instanceof Long) {
            return BigInteger.valueOf((Long) obj);
        }

        if (obj instanceof Float) {
            return BigInteger.valueOf(((Float) obj).longValue());
        }

        if (obj instanceof Double) {
            return BigInteger.valueOf(((Double) obj).longValue());
        }

        if (obj instanceof BigInteger) {
            return ((BigInteger) obj);
        }

        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).toBigInteger();
        }

        if (obj instanceof String) {
            return new BigInteger((String) obj);
        }

        throw Errors.illegalArgument("No se puede convertir '%s' a BigInteger.", obj.getClass().getCanonicalName());
    }

    /**
     * Adapta un valor numerico de tipo indeterminado a un valor concreto de
     * tipo {@link java.math.BigDecimal BigDecimal}.
     *
     * @param obj Valor numerico arbitratio.
     * @return Valor numerico adaptado.
     */
    public static BigDecimal adaptAsBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Short) {
            return new BigDecimal((Short) obj);
        }

        if (obj instanceof Integer) {
            return new BigDecimal((Integer) obj);
        }

        if (obj instanceof Long) {
            return new BigDecimal((Long) obj);
        }

        if (obj instanceof Float) {
            return new BigDecimal((Float) obj);
        }

        if (obj instanceof Double) {
            return new BigDecimal((Double) obj);
        }

        if (obj instanceof BigInteger) {
            return new BigDecimal((BigInteger) obj);
        }

        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }

        if (obj instanceof String) {
            return new BigDecimal((String) obj);
        }

        throw Errors.illegalArgument("No se puede convertir '%s' a BigDecimal.", obj.getClass().getCanonicalName());
    }

    /**
     * Normaliza un valor porcentual entre 0.0 y 100.0 a un valor entre 0.0 y
     * 1.0.
     *
     * @param valor Valor porcentual entre 0.0 y 100.0.
     * @return Valor porcentual normalizado entre 0.0 y 1.0.
     */
    public static BigDecimal normalizarPorcentaje(BigDecimal valor) {
        return div(mul(valor, Numeric.UNO), Numeric.CIEN);
    }

    /**
     * Verifica si un valor numerico es distinto a otro.
     *
     * @param a Valor numerico.
     * @param b Valor numerico.
     * @return {@code true} si {@code a} es distinto a {@code b}, caso contrario
     * {@code false}.
     */
    public static boolean distinto(BigDecimal a, BigDecimal b) {
        return !igual(a, b);
    }

    /**
     * Verifica si un valor numerico es igual a otro.
     *
     * @param a Valor numerico.
     * @param b Valor numerico.
     * @return {@code true} si {@code a} es igual a {@code b}, caso contrario
     * {@code false}.
     */
    public static boolean igual(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return false;
        } else {
            return a.compareTo(b) == 0;
        }
    }

    /**
     * Verifica si un valor numerico es igual a otro.
     *
     * @param a Valor numerico.
     * @param b Valor numerico.
     * @return {@code true} si {@code a} es mayor que {@code b}, caso contrario
     * {@code false}.
     */
    public static boolean mayor(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return false;
        } else {
            return a.compareTo(b) > 0;
        }
    }

    /**
     * Verifica si un valor numerico es igual a otro.
     *
     * @param a Valor numerico.
     * @param b Valor numerico.
     * @return {@code true} si {@code a} es mayor o igual que {@code b}, caso
     * contrario {@code false}.
     */
    public static boolean mayorIgual(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return false;
        } else {
            return a.compareTo(b) >= 0;
        }
    }

    /**
     * Verifica si un valor numerico es igual a otro.
     *
     * @param a Valor numerico.
     * @param b Valor numerico.
     * @return {@code true} si {@code a} es menor que {@code b}, caso contrario
     * {@code false}.
     */
    public static boolean menor(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return false;
        } else {
            return a.compareTo(b) < 0;
        }
    }

    /**
     * Verifica si un valor numerico es igual a otro.
     *
     * @param a Valor numerico.
     * @param b Valor numerico.
     * @return {@code true} si {@code a} es menor o igual que {@code b}, caso
     * contrario {@code false}.
     */
    public static boolean menorIgual(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return false;
        } else {
            return a.compareTo(b) <= 0;
        }
    }

    /**
     * Obtiene el valor menor entre dos valores posibles.
     *
     * @param a Valor numerico.
     * @param b Valor numerico.
     * @return {@code a} si es estrictamente menor que {@code b}. Caso
     * contrario, {@code b}.
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (menor(a, b)) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * Obtiene el valor mayor entre dos valores posibles.
     *
     * @param a Valor numerico.
     * @param b Valor numerico.
     * @return {@code a} si es estrictamente mayor que {@code b}. Caso
     * contrario, {@code b}.
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (mayor(a, b)) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * Verifica si un valor numerico es multiplo de otro.
     *
     * @param a Valor numerico comparado.
     * @param b Valor numerico de multiplo.
     * @return {@code true} si el valor {@code a} es multiplo del valor
     * {@code b}.
     */
    public static boolean esMultiplo(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return false;
        } else {
            BigDecimal mod = a.remainder(b);
            return Numeric.igual(mod, Numeric.CERO);
        }
    }

    /**
     * Toma un valor de tipo {@code String} y retorna su equivalente en el tipo
     * de dato {@code BigDecimal}.
     *
     * @param value Valor numerico.
     * @return valor numerico como {@code BigDecimal}.
     */
    public static BigDecimal wrap(String value) {
        return new BigDecimal(value);
    }

    /**
     * Toma un valor de tipo {@code int} y retorna su equivalente en el tipo de
     * dato {@code BigDecimal}.
     *
     * @param value Valor numerico.
     * @return valor numerico como {@code BigDecimal}.
     */
    public static BigDecimal wrap(int value) {
        return BigDecimal.valueOf(value);
    }

    /**
     * Toma un valor de tipo {@code long} y retorna su equivalente en el tipo de
     * dato {@code BigDecimal}.
     *
     * @param value Valor numerico.
     * @return valor numerico como {@code BigDecimal}.
     */
    public static BigDecimal wrap(long value) {
        return BigDecimal.valueOf(value);
    }

    /**
     * Toma un valor de tipo {@code double} y retorna su equivalente en el tipo
     * de dato {@code BigDecimal}.
     *
     * @param value Valor numerico.
     * @return valor numerico como {@code BigDecimal}.
     */
    public static BigDecimal wrap(double value) {
        return BigDecimal.valueOf(value);
    }

    /**
     * Toma un valor de tipo {@code Number} y retorna su equivalente en el tipo
     * de dato {@code BigDecimal}.
     *
     * @param value Valor numerico.
     * @return valor numerico como {@code BigDecimal}.
     */
    public static BigDecimal wrap(Number value) {
        return new BigDecimal(String.valueOf(value));
    }

    /**
     * Aplica un formateo por defecto a un valor arbitrario.
     *
     * @param value Valor.
     * @return Representacion de texto.
     */
    public static String format(BigDecimal value) {
        if (puedeConsiderarseEntero(value)) {
            BigInteger value0 = value.toBigInteger();
            return String.valueOf(value0);
        } else {
            BigDecimal value0 = value.stripTrailingZeros();
            return String.valueOf(value0);
        }
    }

    /**
     * Obtiene el formato por defecto para valores numericos.
     *
     * @return Formato para valores numericos.
     */
    public static DecimalFormat getDecimalFormat() {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locales.es_ES);
        DecimalFormat numberFormat = new DecimalFormat(DEFAULT_NUMBER_PATTERN, formatSymbols);
        numberFormat.setMaximumFractionDigits(DEFAULT_MAX_FRACTION_DIGITS);
        numberFormat.setMaximumIntegerDigits(DEFAULT_MAX_INTEGER_DIGITS);
        numberFormat.setRoundingMode(DEFAULT_ROUNDING_MODE);
        return numberFormat;
    }

    /**
     * Obtiene el formato para valores numericos con una cantidad de digitos y
     * decimales dado.
     *
     * @param digitos Cantidad de digitos enteros.
     * @param decimales Cantidad de digitos decimales.
     * @return Formato para valores numericos.
     */
    public static DecimalFormat getDecimalFormat(int digitos, int decimales) {
        return getDecimalFormat(digitos, decimales, DEFAULT_ROUNDING_MODE);
    }

    /**
     * Obtiene el formato para valores numericos con una cantidad de digitos y
     * decimales dado.
     *
     * @param digitos Cantidad de digitos enteros.
     * @param decimales Cantidad de digitos decimales.
     * @param redondeo Tipo de redondeo.
     * @return Formato para valores numericos.
     */
    public static DecimalFormat getDecimalFormat(int digitos, int decimales, RoundingMode redondeo) {
        String pattern = buildFormatString(digitos, decimales);

        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locales.es_ES);
        DecimalFormat numberFormat = new DecimalFormat(pattern, formatSymbols);
        numberFormat.setMaximumIntegerDigits(digitos);
        numberFormat.setMaximumFractionDigits(decimales);
        numberFormat.setRoundingMode(redondeo);
        return numberFormat;
    }

    public static String buildFormatString(int digitos, int decimales) {
        String digitosPart = buildParteDigitosFormatString(digitos);
        String decimalesPart = buildParteDecimalesFormatString(decimales);
        if (Assertions.stringNullOrEmpty(digitosPart) == true && Assertions.stringNullOrEmpty(decimalesPart) == true) {
            return "";
        } else if (Assertions.stringNullOrEmpty(digitosPart) == true && Assertions.stringNullOrEmpty(decimalesPart) == false) {
            return "." + decimalesPart;
        } else if (Assertions.stringNullOrEmpty(digitosPart) == false && Assertions.stringNullOrEmpty(decimalesPart) == true) {
            return digitosPart;
        } else if (Assertions.stringNullOrEmpty(digitosPart) == false && Assertions.stringNullOrEmpty(decimalesPart) == false) {
            return digitosPart + "." + decimalesPart;
        } else {
            throw Errors.illegalState();
        }
    }

    private static String buildParteDigitosFormatString(int digitos) {
        if (digitos > 0) {
            Collection<String> parts = Lists.empty();
            int rem = digitos % 3;
            if (rem > 0) {
                String part0 = Padding.repeat('#', rem);
                parts.add(part0);
            }
            int nbl = (digitos - rem) / 3;
            while (nbl > 0) {
                String part1 = Padding.repeat('#', 3);
                parts.add(part1);
                nbl--;
            }
            return Joiner.of(parts).separator(",").join();
        } else {
            return "";
        }
    }

    private static String buildParteDecimalesFormatString(int decimales) {
        if (decimales > 0) {
            return Padding.repeat('#', decimales);
        } else {
            return "";
        }
    }

    /**
     * Calcula la sumatoria de una cantidad indefinida de valores numericos.
     *
     * @param values Valores numericos a sumar.
     * @return Sumatoria total de valores.
     */
    public static BigDecimal sum(BigDecimal... values) {
        BigDecimal sum = Numeric.CERO;
        for (BigDecimal value : values) {
            if (value != null) {
                sum = sum.add(value);
            }
        }
        return sum;
    }

    /**
     * Calcula la diferencia entre una cantidad indefinida de valores numericos.
     *
     * @param values Valores numericos a restar.
     * @return Sumatoria total de valores.
     */
    public static BigDecimal sub(BigDecimal... values) {
        if (values == null || values.length < 2) {
            throw Errors.illegalArgument("Se esperan como minimo dos valores");
        }
        BigDecimal sub = values[0];
        for (int i = 1; i < values.length; i++) {
            BigDecimal value = values[i];
            if (value != null) {
                sub = sub.subtract(value);
            }
        }
        return sub;
    }

    /**
     * Calcula el producto entre una cantidad indefinida de valores numericos.
     *
     * @param values Valores numericos a dividir.
     * @return Producto total de valores.
     */
    public static BigDecimal mul(BigDecimal... values) {
        if (values == null || values.length < 2) {
            throw Errors.illegalArgument("Se esperan como minimo dos valores");
        }
        BigDecimal mul = values[0];
        for (int i = 1; i < values.length; i++) {
            BigDecimal value = values[i];
            if (value != null) {
                mul = mul.multiply(value);
            }
        }
        return mul;
    }

    /**
     * Calcula la division entre una cantidad indefinida de valores numericos.
     *
     * @param values Valores numericos a dividir.
     * @return Division total de valores.
     */
    public static BigDecimal div(BigDecimal... values) {
        if (values == null || values.length < 2) {
            throw Errors.illegalArgument("Se esperan como minimo dos valores");
        }
        BigDecimal div = values[0];
        for (int i = 1; i < values.length; i++) {
            BigDecimal value = values[i];
            if (value != null) {
                div = div.divide(value, DEFAULT_MAX_FRACTION_DIGITS, DEFAULT_ROUNDING_MODE);
            }
        }
        return div;
    }

    /**
     * Calcula la division entre dos valores arbitrarios, pero con la
     * posibilidad de especificar la cantidad maxima de decimales y el tipo de
     * redondeo. Util en contextos monetarios.
     *
     * @param a Divisor.
     * @param b Dividendo.
     * @param cantidadDecimales Cantidad maxima de decimales para el resultado
     * final.
     * @param redondeo Tipo de redondeo.
     * @return Resultado de division.
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b, int cantidadDecimales, RoundingMode redondeo) {
        if (distinto(b, Numeric.CERO)) {
            return a.divide(b, cantidadDecimales, redondeo);
        } else {
            return Numeric.CERO;
        }
    }

    /**
     * Redondea un valor numerico hasta dejarlo sin parte decimal.
     *
     * @param value Valor a redondear.
     * @return Valor redondeado.
     */
    public static BigDecimal round(BigDecimal value) {
        return Numeric.round(value, 0);
    }

    /**
     * Redondea un valor numerico hasta una cierta cantidad de digitos
     * decimales.
     *
     * @param value Valor a redondear.
     * @param scale Cantidad de digitos decimales a mantener.
     * @return Valor redondeado.
     */
    public static BigDecimal round(BigDecimal value, int scale) {
        return Numeric.round(value, scale, DEFAULT_ROUNDING_MODE);
    }

    /**
     * Redondea un valor numerico hasta una cierta cantidad de digitos
     * decimales.
     *
     * @param value Valor a redondear.
     * @param scale Cantidad de digitos decimales a mantener.
     * @param redondeo Tipo de redondeo a aplicar.
     * @return Valor redondeado.
     */
    public static BigDecimal round(BigDecimal value, int scale, RoundingMode redondeo) {
        BigDecimal rounded = value.setScale(scale, redondeo);
        return rounded;
    }

    /**
     * <p>
     * Obtiene el valor que representa el {@code porcentaje} del valor indicado.
     * Se asume que:
     * </p>
     * <ul>
     * <li>{@code value -- 100%}</li>
     * <li>{@code x -- porcentaje}</li>
     * </ul>
     * <p>
     * Por tanto {@code x = (value * porcentaje) / 100}.
     * </p>
     *
     * @param value Valor original que representa el 100%.
     * @param porcentaje Porcentaje que se desea calcular.
     * @return Valor.
     */
    public static BigDecimal montoDePorcentaje(BigDecimal value, BigDecimal porcentaje) {
        return montoDePorcentaje(value, porcentaje, DEFAULT_MAX_FRACTION_DIGITS, DEFAULT_ROUNDING_MODE);
    }

    /**
     * <p>
     * Obtiene el valor que representa el {@code porcentaje} del valor indicado,
     * pero con la posibilidad de especificar la cantidad maxima de decimales y
     * el tipo de redondeo. Util en contextos monetarios.
     * </p>
     * <p>
     * Se asume que:
     * </p>
     * <ul>
     * <li>{@code value -- 100%}</li>
     * <li>{@code x -- porcentaje}</li>
     * </ul>
     * <p>
     * Por tanto {@code x = (value * porcentaje) / 100}.
     * </p>
     *
     * @param value Valor original que representa el 100%.
     * @param porcentaje Porcentaje que se desea calcular.
     * @param cantidadDecimales Cantidad maxima de decimales para el resultado
     * final.
     * @param redondeo Tipo de redondeo.
     * @return Valor.
     */
    public static BigDecimal montoDePorcentaje(BigDecimal value, BigDecimal porcentaje, int cantidadDecimales, RoundingMode redondeo) {
        BigDecimal y = Numeric.mul(value, porcentaje);
        BigDecimal x = y.divide(Numeric.CIEN, cantidadDecimales, redondeo);
        return x;
    }

    /**
     * <p>
     * Obtiene el porcentaje que representa la {@code parte} del valor indicado.
     * Se asume que:
     * </p>
     * <ul>
     * <li>{@code value -- 100%}</li>
     * <li>{@code parte -- x}</li>
     * </ul>
     * <p>
     * Por tanto {@code x = (parte * 100) / value}.
     * </p>
     *
     * @param value Valor original que representa el 100%.
     * @param parte Parte del valor original.
     * @return Valor porcentual.
     */
    public static BigDecimal porcentajeDeMonto(BigDecimal value, BigDecimal parte) {
        return porcentajeDeMonto(value, parte, DEFAULT_MAX_FRACTION_DIGITS, DEFAULT_ROUNDING_MODE);
    }

    /**
     * <p>
     * Obtiene el porcentaje que representa la {@code parte} del valor indicado,
     * pero con la posibilidad de especificar la cantidad maxima de decimales y
     * el tipo de redondeo.Util en contextos monetarios. Se asume que:
     * </p>
     * <ul>
     * <li>{@code value -- 100%}</li>
     * <li> {@code parte -- x}</li>
     * </ul>
     * <p>
     * Por tanto {@code x = (parte * 100) / value}.
     * </p>
     *
     * @param value Valor original que representa el 100%.
     * @param parte Parte del valor original.
     * @param cantidadDecimales Cantidad maxima de decimales para el resultado
     * final.
     * @param redondeo Tipo de redondeo.
     * @return Valor porcentual.
     */
    public static BigDecimal porcentajeDeMonto(BigDecimal value, BigDecimal parte, int cantidadDecimales, RoundingMode redondeo) {
        // Control de seguridad para evitar division por cero.
        if (Numeric.igual(value, Numeric.CERO)) {
            return Numeric.CERO;
        }

        BigDecimal y = Numeric.mul(parte, Numeric.CIEN);
        BigDecimal x = y.divide(value, cantidadDecimales, redondeo);
        return x;
    }

    public static int digits(int num) {
        Integer x = num;
        return digits(x.longValue());
    }

    public static int digits(long num) {
        return digits(BigInteger.valueOf(num));
    }

    // Fuente: https://stackoverflow.com/a/27665291
    public static int digits(BigInteger num) {
        if (num.equals(BigInteger.ZERO)) {
            return 1;
        }

        if (num.compareTo(BigInteger.ZERO) < 0) {
            num = num.multiply(BigInteger.valueOf(-1));
        }

        return log(BigInteger.valueOf(10), num).intValue() + 1;
    }

    // Fuente: https://stackoverflow.com/a/27665291
    private static BigInteger log(BigInteger base, BigInteger num) {
        /**
         * The technique tries to get the products among the squares of base
         * close to the actual value as much as possible without exceeding it.
         */
        BigInteger resultSet = BigInteger.ZERO;
        BigInteger actMult = BigInteger.ONE;
        BigInteger lastMult = BigInteger.ONE;
        BigInteger actor = base;
        BigInteger incrementor = BigInteger.ONE;
        while (actMult.multiply(base).compareTo(num) < 1) {
            int count = 0;
            while (actMult.multiply(actor).compareTo(num) < 1) {
                lastMult = actor; //Keep the old squares
                actor = actor.multiply(actor); //Square the base repeatedly until the value exceeds
                if (count > 0) {
                    incrementor = incrementor.multiply(BigInteger.valueOf(2));
                }
                //Update the current exponent of the base
                count++;
            }
            if (count == 0) {
                break;
            }

            /* If there is no way to multiply the "actMult"
         * with squares of the base (including the base itself)
         * without keeping it below the actual value,
         * it is the end of the computation
             */
            actMult = actMult.multiply(lastMult);
            resultSet = resultSet.add(incrementor);
            /* Update the product and the exponent
         * */
            actor = base;
            incrementor = BigInteger.ONE;
            //Reset the values for another iteration
        }
        return resultSet;
    }

}
