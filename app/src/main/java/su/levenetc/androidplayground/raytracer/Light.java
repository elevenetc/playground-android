package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

public class Light {

    protected final double x;
    protected final double y;
    protected List<Ray> rays = new LinkedList<>();

    public Light(double x, double y) {
        this.x = x;
        this.y = y;
    }
}