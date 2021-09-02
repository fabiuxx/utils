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
        Color color = Color.decode(rgbHex);
        double percentage = factor / 100.0;
        int r = (int) Math.round(Math.min(255.0, color.getRed() + 255.0 * percentage));
        int g = (int) Math.round(Math.min(255.0, color.getGreen() + 255.0 * percentage));
        int b = (int) Math.round(Math.min(255.0, color.getBlue() + 255.0 * percentage));
        String rgb = String.format("#%02x%02x%02x", r, g, b);
        return rgb.toUpperCase();
    }

}
