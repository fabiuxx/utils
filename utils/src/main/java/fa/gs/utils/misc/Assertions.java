/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.numeric.Numeric;
import fa.gs.utils.misc.text.Strings;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Assertions {

    public static boolean any(Boolean... values) {
        if (!Assertions.isNullOrEmpty(values)) {
            for (Boolean value : values) {
                if (value == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean all(Boolean... values) {
        if (!Assertions.isNullOrEmpty(values)) {
            for (Boolean value : values) {
                if (value == false) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean anyNull(Object... values) {
        if (!Assertions.isNullOrEmpty(values)) {
            for (Object value : values) {
                if (value == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean allNull(Object... values) {
        if (!Assertions.isNullOrEmpty(values)) {
            for (Object value : values) {
                if (value == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    public static void raiseIfNull(Object object) throws IllegalArgumentException {
        if (object == null) {
            IllegalArgumentException npe = Errors.illegalArgument();
            Errors.popStackTrace(npe, 1);
            throw npe;
        }
    }

    public static boolean isNull(Object object) {
        return (object == null);
    }

    public static boolean notNull(Object object) {
        return (object != null);
    }

    public static Object coalesce(Object... args) {
        if (isNullOrEmpty(args)) {
            return null;
        }

        for (Object arg : args) {
            if (arg != null) {
                return arg;
            }
        }

        return null;
    }
    
    public static boolean in(String value, String[] values) {
        for(String value0 : values) {
            if(Objects.equals(value, value0)) {
                return true;
            }
        }
        return false;
    }

    public static boolean stringNullOrEmpty(String value) {
        return (value == null || value.isEmpty());
    }

    public static boolean isRuc(String value) {
        Pattern pattern = Pattern.compile("[0-9]+\\-[0-9]$");
        Matcher matcher = pattern.matcher((value));
        return matcher.matches();
    }

    public static boolean isNumeroFactura(String value) {
        Pattern pattern = Pattern.compile("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d\\d\\d\\d");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$");
        Matcher matcher = pattern.matcher((email));
        return matcher.matches();
    }

    public static boolean integerMinValue(Integer value, Integer min) {
        return (value >= min);
    }

    public static boolean integerInRange(Integer value, Integer min, Integer max) {
        return (value >= min) && (value <= max);
    }

    public static boolean isInteger(String value) {
        try {
            Integer number = Integer.valueOf(value);
            return number != null;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveInteger(String value) {
        try {
            Integer number = Integer.valueOf(value);
            return number != null && number > 0;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveInteger(String value) {
        try {
            Integer number = Integer.valueOf(value);
            return number != null && number >= 0;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveInteger(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isPositiveInteger(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveInteger(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isZeroOrPositiveInteger(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isLong(String value) {
        try {
            Long number = Long.valueOf(value);
            return number != null;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveLong(String value) {
        try {
            Long number = Long.valueOf(value);
            return number != null && number > 0L;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveLong(String value) {
        try {
            Long number = Long.valueOf(value);
            return number != null && number >= 0L;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveLong(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isPositiveLong(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveLong(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isZeroOrPositiveLong(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isBigInteger(String value) {
        try {
            BigInteger bi = Numeric.adaptAsBigInteger(value);
            return bi != null;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveBigInteger(String value) {
        try {
            BigInteger bi = Numeric.adaptAsBigInteger(value);
            return (bi != null && bi.compareTo(BigInteger.ZERO) > 0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveBigInteger(String value) {
        try {
            BigInteger bi = Numeric.adaptAsBigInteger(value);
            return (bi != null && bi.compareTo(BigInteger.ZERO) >= 0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveBigInteger(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isPositiveBigInteger(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveBigInteger(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isZeroOrPositiveBigInteger(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isDecimal(String value) {
        try {
            Double.valueOf(value);
            return true;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveDecimal(String value) {
        try {
            Double number = Double.valueOf(value);
            return number > 0.0;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveDecimal(String value) {
        try {
            Double number = Double.valueOf(value);
            return number >= 0.0;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveDecimal(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isPositiveDecimal(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveDecimal(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isZeroOrPositiveDecimal(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isBigDecimal(String value) {
        try {
            BigDecimal bi = Numeric.adaptAsBigDecimal(value);
            return bi != null;
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveBigDecimal(String value) {
        try {
            BigDecimal bi = Numeric.adaptAsBigDecimal(value);
            return (bi != null && bi.compareTo(BigDecimal.ZERO) > 0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveBigDecimal(String value) {
        try {
            BigDecimal bi = Numeric.adaptAsBigDecimal(value);
            return (bi != null && bi.compareTo(BigDecimal.ZERO) >= 0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isPositiveBigDecimal(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isPositiveBigDecimal(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isZeroOrPositiveBigDecimal(Number value) {
        try {
            String value0 = String.valueOf(value);
            return isZeroOrPositiveBigDecimal(value0);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isDate(int anho, int mes, int dia) {
        String date = Strings.format("%04d/%02d/%02d", anho, mes, dia);
        return isDate(date, "yyyy/MM/dd");
    }

    public static boolean isDate(String date, String pattern) {
        try {
            // Verificar si es posible parsear la fecha.
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            Date date0 = sdf.parse(date);

            // Ambas cadenas deben ser iguales.
            String date2 = sdf.format(date0);
            return Objects.equals(date, date2);
        } catch (Throwable thr) {
            return false;
        }
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNullOrEmpty(Map<?, ?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static <T> boolean isNullOrEmpty(T[] collection) {
        return (collection == null || collection.length == 0);
    }

    public static boolean isNullOrEmpty(byte[] collection) {
        return (collection == null || collection.length == 0);
    }

}
