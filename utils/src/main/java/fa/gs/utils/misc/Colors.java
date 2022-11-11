/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.misc.text.Strings;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Colors {

    /**
     * Calcula la luminosidad de un color dado.
     *
     * @param rgbHex Definición de color en hexadecimal.
     * @return Luminancia de color.
     */
    private static float luminance(String rgbHex) {
        Color color = decode(rgbHex);
        float r = color.getRed();
        float g = color.getGreen();
        float b = color.getBlue();
        return (0.2126f * r + 0.7152f * g + 0.0722f * b);
    }

    /**
     * "Aclara" un color en un factor dado.
     *
     * @param rgbHex Definicion de color en hexadecimal.
     * @param factor Factor de aclarado.
     * @return Color aclarado, en hexadecimal.
     */
    public static String ligthen(String rgbHex, int factor) {
        Color color = decode(rgbHex);
        double percentage = factor / 100.0;
        int r = (int) Math.floor(Math.min(255.0, color.getRed() + 255.0 * percentage));
        int g = (int) Math.floor(Math.min(255.0, color.getGreen() + 255.0 * percentage));
        int b = (int) Math.floor(Math.min(255.0, color.getBlue() + 255.0 * percentage));
        String rgb = Strings.format("#%02x%02x%02x", r, g, b);
        return rgb.toUpperCase();
    }

    public static Color decode(String rgbHex) {
        if (rgbHex.startsWith("#")) {
            rgbHex = rgbHex.substring(1);
        }

        String r = rgbHex.substring(0, 2);
        String g = rgbHex.substring(2, 4);
        String b = rgbHex.substring(4, 6);
        return new Color(Integer.valueOf(r, 16), Integer.valueOf(g, 16), Integer.valueOf(b, 16));
    }

    /**
     * Genera una definicion de color en hexadecimal, de forma aleatoria.
     *
     * @param r Generador de aleatoriedad.
     * @return Definicion de color en hexadecimal.
     */
    public String random(Random r) {
        int n = r.nextInt(0xffffff + 1);
        return String.format("#%06x", n);
    }

    /**
     * Mapea una cadena de texto arbitraria a una cadena hexadecimal RGB.
     *
     * @param text Texto original.
     * @return Cadena RGB Hexadecimal.
     */
    public static String deriveFromText(String text) {
        return deriveFromText(text, false);
    }

    /**
     * <p>
     * Mapea una cadena de texto arbitraria a una cadena hexadecimal RGB.
     * </p>
     * <p>
     * Fuente: https://stackoverflow.com/a/16348977
     * </p>
     *
     * @param text Texto original.
     * @param monotone Si se debe generar la misma cadena RGB independientemente
     * a las mayusculas o minusculas.
     * @return Cadena RGB Hexadecimal.
     */
    public static String deriveFromText(String text, boolean monotone) {
        // Invariante ante mayusculas o minusculas.
        if (monotone) {
            text = text.toLowerCase();
        }

        // Generar hash.
        int hash = 0;
        for (int i = 0; i < text.length(); i++) {
            hash = text.charAt(i) + ((hash << 5) - hash);
        }

        // Genarar color rgb.
        String color = "#";
        for (int i = 0; i < 3; i++) {
            int value = (hash >> (i * 8)) & 0xFF;
            color = color + Strings.format("%02x", value);
        }

        return color;
    }

    /**
     * <p>
     * Dada una cadena RGB hexadecimal, se determina el color de texto apropiado
     * para el color indicado como fondo.
     * </p>
     * <p>
     * Fuente: https://stackoverflow.com/a/49092130.
     * </p>
     *
     * @param backgroundColor Color de fondo.
     * @return Color para texto.
     */
    public static String deriveForegroundColor(String backgroundColor) {
        float L = luminance(backgroundColor);
        return (L < 140.0) ? "#fff" : "#000";
    }

}
