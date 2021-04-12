/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.geo;

import fa.gs.utils.collections.Lists;
import java.util.Collection;
import lombok.Getter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Polyline {

    @Getter
    private final Collection<Point> points;

    public Polyline() {
        this.points = Lists.empty();
    }

    public void add(Point point) {
        if (point != null) {
            this.points.add(point);
        }
    }

    /**
     * Decodifica una cadena procesada por el algoritmo de google maps para
     * representar una serie de puntos. Fuente:
     * https://github.com/gsanthosh91/DecodePolyline
     *
     * @param str Cadena codificada utilizando algoritmo de codificacion de
     * google maps.
     * @return Polilinea.
     */
    public static Polyline decodeGoogleMapsPolyline(String str) {
        Polyline polyline = new Polyline();
        double precision = 1e-5;

        int index = 0;
        int lat = 0, lng = 0;

        while (index < str.length()) {
            int b, shift = 0, result = 0;
            do {
                b = str.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = str.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            Point point = new Point((double) lat / precision, (double) lng / precision);
            polyline.add(point);
        }

        return polyline;
    }

}
