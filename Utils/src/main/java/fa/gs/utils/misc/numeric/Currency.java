/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.numeric;

import fa.gs.utils.misc.Assertions;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Currency {

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
        String txt = Numeric.getDecimalFormat().format(value);
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
            DecimalFormat numberFormat = Numeric.getDecimalFormat();
            numberFormat.setParseBigDecimal(true);
            return (BigDecimal) numberFormat.parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Currency.Formatter formatter() {
        return new Currency.Formatter();
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
            // Redondear valor.
            if (cantidadDecimales > 0) {
                valor = valor.setScale(cantidadDecimales, redondeo);
            }

            // Formatear.
            DecimalFormat numberFormat = Numeric.getDecimalFormat(30, cantidadDecimales, redondeo);
            String format0 = numberFormat.format(valor);
            if (!Assertions.stringNullOrEmpty(simbolo)) {
                format0 = (format0 + " " + simbolo);
            }
            return format0.trim();
        }

    }

}
