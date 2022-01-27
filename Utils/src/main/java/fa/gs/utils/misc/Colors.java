/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

import java.awt.Color;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Colors {

    public static String ligthen(String rgbHex, int factor) {
        Color color = decode(rgbHex);
        double percentage = factor / 100.0;
        int r = (int) Math.floor(Math.min(255.0, color.getRed() + 255.0 * percentage));
        int g = (int) Math.floor(Math.min(255.0, color.getGreen() + 255.0 * percentage));
        int b = (int) Math.floor(Math.min(255.0, color.getBlue() + 255.0 * percentage));
        String rgb = String.format("#%02x%02x%02x", r, g, b);
        return rgb.toUpperCase();
    }

    public static Color decode(String rgbHex) {
        String r = rgbHex.substring(1, 3);
        String g = rgbHex.substring(3, 5);
        String b = rgbHex.substring(5, 7);
        return new Color(Integer.valueOf(r, 16), Integer.valueOf(g, 16), Integer.valueOf(b, 16));
    }

}
