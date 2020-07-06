/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.Locales;
import fa.gs.utils.misc.text.Padding;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Currency {

    public static Currency.Formatter formatter() {
        return new Currency.Formatter();
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

    public static class Formatter {

        private BigDecimal valor;
        private String simbolo;
        private int cantidadDecimales;
        private RoundingMode redondeo;

        private Formatter() {
            this.valor = Numeric.CERO;
            this.simbolo = "";
            this.cantidadDecimales = 0;
            this.redondeo = RoundingMode.HALF_EVEN;
        }

        public Currency.Formatter valor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public Currency.Formatter simbolo(String simbolo) {
            this.simbolo = simbolo;
            return this;
        }

        public Currency.Formatter cantidadDecimales(int cantidadDecimales) {
            this.cantidadDecimales = cantidadDecimales;
            return this;
        }

        public Currency.Formatter redondeo(RoundingMode redondeo) {
            this.redondeo = redondeo;
            return this;
        }

        public String format() {
            // 1. Redondear valor.
            if (cantidadDecimales > 0) {
                valor = valor.setScale(cantidadDecimales, redondeo);
            }

            // 2. Generar cadena de formateo.
            String pattern = Currency.buildFormatString(20, cantidadDecimales);

            // 3. Generar formateador especifico.
            DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locales.es_ES);
            DecimalFormat numberFormat = new DecimalFormat(pattern, formatSymbols);
            numberFormat.setMaximumFractionDigits(cantidadDecimales);
            numberFormat.setMaximumIntegerDigits(20);
            numberFormat.setRoundingMode(redondeo);

            // 4. Formatear.
            if (Assertions.stringNullOrEmpty(pattern)) {
                return String.valueOf(valor);
            } else {
                String format0 = numberFormat.format(valor);
                if (!Assertions.stringNullOrEmpty(simbolo)) {
                    format0 = (format0 + " " + simbolo);
                }
                return format0.trim();
            }
        }

    }

}
