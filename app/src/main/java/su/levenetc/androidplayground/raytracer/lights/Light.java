package su.levenetc.androidplayground.raytracer.lights;

import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.RayMath;

public class Light {

    public List<Ray> rays = new LinkedList<>();
    protected double x;
    protected double y;

    public Light(double x, double y) {
        this.x = x;
        this.y = y;
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

    protected void rotateInitVectors(boolean biased) {
        double coneAngle = 45d;
        double stepAngle = coneAngle / rays.size();

        double angle = 0;

        for (int i = 0; i < rays.size(); i++) {
            RayMath.rotateSegment(this.rays.get(i).initVector, angle + (biased ? getDirectionBias() : 0) - coneAngle / 2);
            angle += stepAngle;
        }
    }

    private double getDirectionBias() {
        return Math.random() * 0.5;
    }

    protected void initRays(int raysCount, double dirX, double dirY) {

        //define end locations towards direction
        //should be long to test intersections later
        double ratio = 100;
        double dx = dirX - x;
        double dy = dirY - y;
        double endX = x + dx * ratio;
        double endY = y + dy * ratio;

        for (int i = 0; i < raysCount; i++) {
            this.rays.add(new Ray(x, y, endX, endY));
        }
    }

}