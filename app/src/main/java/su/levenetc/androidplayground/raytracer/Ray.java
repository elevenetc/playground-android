package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Ray {

    private List<RaySegment> reflectedOrRefracted = new LinkedList<>();
    public RaySegment initSegment = new RaySegment();
    public double length;//px

    public Ray(double x1, double y1, double x2, double y2, int color) {
        init(x1, y1, x2, y2);
        initSegment.startColor = color;
        initSegment.endColor = color;
    }

    public void init(double x1, double y1, double x2, double y2) {
        initSegment.set(x1, y1, x2, y2);
        //TODO: cache/pool
        reflectedOrRefracted.clear();
        length = RayMath.distance(x1, y1, x2, y2);
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
}
