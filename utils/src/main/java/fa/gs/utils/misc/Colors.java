/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.text.Strings;
import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Colors {

    /**
     * Calcula la luminosidad de un color dado.
     *
     * @param color Color.
     * @return Luminancia de color.
     */
    private static float luminance(Color color) {
        float r = color.getRed();
        float g = color.getGreen();
        float b = color.getBlue();
        return (0.2126f * r + 0.7152f * g + 0.0722f * b);
    }

    private static double guard(double value) {
        if (value > 1.0) {
            value = 1.0;
        }
        if (value < 0.0) {
            value = 0.0;
        }
        return value;
    }

    /**
     * "Aclara" un color en un factor dado.
     *
     * @param color Color a aclarar.
     * @param factor Factor de aclarado. Entre 0 y 1.
     * @return Color aclarado.
     */
    public static Color ligthen(Color color, double factor) {
        factor = guard(factor);
        double percentage = factor / 100.0;
        int r = (int) Math.floor(Math.min(255.0, color.getRed() + 255.0 * percentage));
        int g = (int) Math.floor(Math.min(255.0, color.getGreen() + 255.0 * percentage));
        int b = (int) Math.floor(Math.min(255.0, color.getBlue() + 255.0 * percentage));
        return new Color(r, g, b);
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

    public static String encode(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        String rgbHex = Strings.format("#%02X%02X%02X", r, g, b);
        return rgbHex.toUpperCase();
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
     * Genera N colores distribuidos en el espectro del arcoiris.
     *
     * @param N Cantidad de colores a generar.
     * @return Colores generados.
     */
    public static List<Color> random(int N) {
        float step = 360f / N;
        List<Color> colors = Lists.empty();
        for (int i = 0; i < N; i++) {
            float h = i * step;
            int[] rgb = toRGB(h);
            Color color = new Color(rgb[0], rgb[1], rgb[2]);
            colors.add(color);
        }
        return colors;
    }

    /**
     * Dado un angulo entre 0 y 360, calcula el color correspondiente asumiendo
     * una rueda del espectro RGB. Fuente:
     * http://www.cs.utsa.edu/~cs1063/projects/Spring2011/Project2/project2.html
     *
     * @param angle Angulo de rotacion.
     * @return Componentes r,g,b de color asociado.
     */
    private static int[] toRGB(double angle) {
        int r = 0, g = 0, b = 0;
        if (angle >= 0 && angle <= 60) {
            r = 255;
            g = (int) (255 * angle / 60);
            b = 0;
        }

        if (angle >= 61 && angle <= 120) {
            r = (int) (255 * (120 - angle) / 60);
            g = 255;
            b = 0;
        }

        if (angle >= 121 && angle <= 180) {
            r = 0;
            g = 255;
            b = (int) (255 * (angle - 120) / 60);
        }

        if (angle >= 181 && angle <= 240) {
            r = 0;
            g = (int) (255 * (240 - angle) / 60);
            b = 255;
        }

        if (angle >= 241 && angle <= 300) {
            r = (int) (255 * (angle - 240) / 60);
            g = 0;
            b = 255;
        }

        if (angle >= 301 && angle <= 360) {
            r = 255;
            g = 0;
            b = (int) (255 * (360 - angle) / 60);
        }

        return new int[]{r, g, b};
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
        Color color = decode(backgroundColor);
        float L = luminance(color);
        return (L < 140.0) ? "#fff" : "#000";
    }

}
