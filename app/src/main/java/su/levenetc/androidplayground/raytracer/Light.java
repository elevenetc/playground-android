package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

public class Light {

    protected double x;
    protected double y;
    protected List<Ray> rays = new LinkedList<>();

    public Light(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void updatePosition(double x, double y) {

    }

    public List<Ray> rays(){
        return rays;
    }
}