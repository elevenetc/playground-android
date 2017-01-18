package su.levenetc.androidplayground.models;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by eugene.levenetc on 18/01/2017.
 */

public class MapLocation {
    public LatLng geo;
    public Point screen = new Point();

    public MapLocation(LatLng geo) {
        this.geo = geo;
    }
}