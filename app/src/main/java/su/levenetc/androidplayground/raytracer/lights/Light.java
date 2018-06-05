package su.levenetc.androidplayground.raytracer.lights;

import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.Ray;

public class Light {

    public List<Ray> rays = new LinkedList<>();
    protected double x;
    protected double y;
    protected int raysCount;

    /**
     * @return 0.0 to 1.0
     */
    public float brightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public Light(double x, double y, int color, int raysCount) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.raysCount = raysCount;
    }

    public void updatePosition(double x, double y) {

        double dx = x - this.x;
        double dy = y - this.y;

        this.x = x;
        this.y = y;

        for (Ray ray : rays) ray.initSegment.translate(dx, dy);
    }

    public List<Ray> rays() {
        return rays;
    }

    protected int color;
    protected float brightness = 1f;
}