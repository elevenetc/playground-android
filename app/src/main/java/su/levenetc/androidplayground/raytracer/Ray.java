package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Ray {

    private List<RaySegment> reflectedOrRefracted = new LinkedList<>();
    public RaySegment initSegment = new RaySegment();
    public final double length;

    public Ray(double x1, double y1, double x2, double y2, int color) {
        this.length = distance(x1, y1, x2, y2);
        init(x1, y1, x2, y2);
        initSegment.color = color;
    }

    public void init(double x1, double y1, double x2, double y2) {
        initSegment.set(x1, y1, x2, y2);
        //TODO: cache/pool
        reflectedOrRefracted.clear();
    }

    public List<RaySegment> reflectedOrRefracted() {
        return reflectedOrRefracted;
    }


    public RaySegment initVector() {
        return initSegment;
    }

    /**
     * Resets reflected and refracted segments
     */
    public void reset() {
        reflectedOrRefracted.clear();
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }
}
