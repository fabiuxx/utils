/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.geo;

import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class Point {

    private double lat;
    private double lng;

    public Point() {
        this(0.0, 0.0);
    }

    public Point(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

}
