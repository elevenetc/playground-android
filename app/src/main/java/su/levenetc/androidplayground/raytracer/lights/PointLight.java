package su.levenetc.androidplayground.raytracer.lights;

import su.levenetc.androidplayground.raytracer.Ray;

public class PointLight extends Light {

    public PointLight(double x, double y, double radius, int raysCount) {
        super(x, y, raysCount);
        this.radius = radius;
        initRays();
    }

    double radius;

    private void initRays() {

        double angle = 0;
        double angleStep = (Math.PI * 2) / raysCount;

        for (int i = 0; i < raysCount; i++) {

            double dx = radius * Math.cos(angle) + x;
            double dy = radius * Math.sin(angle) + y;

            rays.add(new Ray(x, y, dx, dy));

            angle += angleStep;
        }
    }
}