package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Ray {

    RaySegment initVector = new RaySegment();
    List<RaySegment> raySegments = new LinkedList<>();
    public double length = 1200;//px

    public Ray() {

    }

    public Ray(double x1, double y1, double x2, double y2) {
        init(x1, y1, x2, y2);
    }

    public void init(double x1, double y1, double x2, double y2) {
        initVector.set(x1, y1, x2, y2);
        //TODO: cache/pool
        raySegments.clear();
    }

    public List<RaySegment> lines() {
        return raySegments;
    }

    public RaySegment initVector() {
        return initVector;
    }
}
