package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Ray {
    Vector initVector = new Vector();
    List<Vector> vectors = new LinkedList<>();

    public void init(double x1, double y1, double x2, double y2) {
        initVector.set(x1, y1, x2, y2);
        //TODO: cache/pool
        vectors.clear();
    }
}
