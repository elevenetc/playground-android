package su.levenetc.androidplayground.raytracer.lights;

import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.Ray;

public class Light {

    public List<Ray> rays = new LinkedList<>();
    protected double x;
    protected double y;
    protected int raysCount;

    public Light(double x, double y, int raysCount) {
        this.x = x;
        this.y = y;
        this.raysCount = raysCount;
    }

    public void updatePosition(double x, double y) {

        double dx = x - this.x;
        double dy = y - this.y;

        this.x = x;
        this.y = y;

        for (Ray ray : rays) ray.initVector.translate(dx, dy);
    }

    public List<Ray> rays() {
        return rays;
    }


}