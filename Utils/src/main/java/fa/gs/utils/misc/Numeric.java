/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Numeric {

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    /**
     * Configuracion regional.
     */
    private static final Locale locale = new Locale("es", "ES");

    /**
     * Patron para formateo de valores numericos a cadenas de texto.
     * Correspondiente con los valores establecidos para
     * {@link Numeric#MAX_INTEGER_DIGITS MAX_INTEGER_DIGITS} y
     * {@link Numeric#MAX_FRACTION_DIGITS MAX_FRACTION_DIGITS}.
     */
    public static final String pattern = "###,###,###,###,###.####################";

    /**
     * Divisor para calcular el monto total sin iva 5%.
     */
    public static final String IVA_05_DIVISOR = "1.05";

    /**
     * Divisor para calcular el monto total sin iva 10%.
     */
    public static final String IVA_10_DIVISOR = "1.10";

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
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

    /**
     * Cantidad maxima de digitos en la parte entera de un numero real.
     */
    public static final int MAX_INTEGER_DIGITS = 15;

    /**
     * Cantidad maxima de digitos en la parte decimal de un numero real.
     */
    public static final int MAX_FRACTION_DIGITS = 20;
    //</editor-fold>

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
            return (Integer) obj;
        }

        if (obj instanceof Integer) {
            return (Integer) obj;
        }

        if (obj instanceof Long) {
            return ((Long) obj).intValue();
        }

        if (obj instanceof BigInteger) {
            return ((BigInteger) obj).intValue();
        }

        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValue();
        }

        throw new IllegalArgumentException(String.format("No se puede convertir '%s' a Integer.", obj.getClass().getCanonicalName()));
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
            return (Long) obj;
        }

        if (obj instanceof Integer) {
            return (Long) obj;
        }

        if (obj instanceof Long) {
            return (Long) obj;
        }

        if (obj instanceof BigInteger) {
            return ((BigInteger) obj).longValue();
        }

        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).longValue();
        }

        throw new IllegalArgumentException(String.format("No se puede convertir '%s' a Long.", obj.getClass().getCanonicalName()));
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

        if (obj instanceof BigInteger) {
            return ((BigInteger) obj);
        }

        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).toBigInteger();
        }

        throw new IllegalArgumentException(String.format("No se puede convertir '%s' a BigInteger.", obj.getClass().getCanonicalName()));
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

        throw new IllegalArgumentException(String.format("No se puede convertir '%s' a BigDecimal.", obj.getClass().getCanonicalName()));
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
        return a.compareTo(b) == 0;
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
        return a.compareTo(b) > 0;
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
        return a.compareTo(b) >= 0;
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
        return a.compareTo(b) < 0;
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
        return a.compareTo(b) <= 0;
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
     * Obtiene el formato por defecto para valores numericos.
     *
     * @return Formato para valores numericos.
     */
    public static DecimalFormat getDecimalFormat() {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(locale);
        DecimalFormat numberFormat = new DecimalFormat(pattern, formatSymbols);
        numberFormat.setMaximumFractionDigits(MAX_FRACTION_DIGITS);
        numberFormat.setMaximumIntegerDigits(MAX_INTEGER_DIGITS);
        numberFormat.setRoundingMode(DEFAULT_ROUNDING_MODE);
        return numberFormat;
    }

    /**
     * Toma un valor numerico y retorna una representacion en cadena del mismo
     * como valor monetario.
     *
     * @param value Valor numerico que representa un monto.
     * @return Cadena formateada.
     */
    public static String formatCurrency(BigDecimal value) {
        return formatCurrency(value, null);
    }

    /**
     * Toma un valor numerico y retorna una representacion en cadena del mismo
     * como valor monetario.
     *
     * @param value Valor numerico que representa un monto.
     * @param moneda Definicion de tipo de moneda.
     * @return Cadena formateada.
     */
    public static String formatCurrency(BigDecimal value, String moneda) {
        String txt = getDecimalFormat().format(value);
        if (!Assertions.stringNullOrEmpty(moneda)) {
            txt = String.format("%s %s", moneda, txt);
        }
        return txt.trim();
    }

    /**
     * Toma una representacion en cadena de un valor monetario y retorna el
     * valor numerico correspondiente.
     *
     * @param value Cadena formateada.
     * @return Valor numerico que representa un monto.
     */
    public static BigDecimal parseCurrency(String value) {
        try {
            DecimalFormat numberFormat = getDecimalFormat();
            numberFormat.setParseBigDecimal(true);
            return (BigDecimal) numberFormat.parse(value);
        } catch (Exception e) {
            return null;
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
            throw new IllegalArgumentException("Se esperan como minimo dos valores");
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
            throw new IllegalArgumentException("Se esperan como minimo dos valores");
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
            throw new IllegalArgumentException("Se esperan como minimo dos valores");
        }
        BigDecimal div = values[0];
        for (int i = 1; i < values.length; i++) {
            BigDecimal value = values[i];
            if (value != null) {
                div = div.divide(value, MAX_FRACTION_DIGITS, DEFAULT_ROUNDING_MODE);
            }
        }
        return div;
    }

    /**
     * Redondea un valor numerico hasta dejaron sin parte decimal.
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
        BigDecimal rounded = value.setScale(scale, DEFAULT_ROUNDING_MODE);
        return rounded;
    }
}
