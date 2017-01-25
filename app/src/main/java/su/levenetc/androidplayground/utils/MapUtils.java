package su.levenetc.androidplayground.utils;

import su.levenetc.androidplayground.models.MapLocation;

/**
 * Created by eugene.levenetc on 24/01/2017.
 */

public class MapUtils {

    public static float calcAngleWithEquator(MapLocation locA, MapLocation locB) {
        double latA = locA.geo.latitude;
        double lonA = locA.geo.longitude;
        double latB = locB.geo.latitude;
        double lonB = locB.geo.longitude;
        double latC = latB;
        double lonC = lonA;
    }

    public static double equatorLength(float dp256, float zoom) {
        return dp256 * Math.pow(2, zoom);
    }
}
