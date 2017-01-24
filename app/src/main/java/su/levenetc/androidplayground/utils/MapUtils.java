package su.levenetc.androidplayground.utils;

/**
 * Created by eugene.levenetc on 24/01/2017.
 */

public class MapUtils {
    public static double equatorLength(float dp256, float zoom) {
        return dp256 * Math.pow(2, zoom);
    }
}
