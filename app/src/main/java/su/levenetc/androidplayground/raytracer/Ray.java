package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Ray {

    public RaySegment initVector = new RaySegment();
    public double length;//px

    public void init(double x1, double y1, double x2, double y2) {
        initVector.set(x1, y1, x2, y2);
        //TODO: cache/pool
        reflectedOrRefracted.clear();
        length = RayMath.distance(x1, y1, x2, y2);
    }

    public Ray(double x1, double y1, double x2, double y2) {
        init(x1, y1, x2, y2);
    }

    public List<RaySegment> lines() {
        return reflectedOrRefracted;
    }

    List<RaySegment> reflectedOrRefracted = new LinkedList<>();

    public RaySegment initVector() {
        return initVector;
    }
}
