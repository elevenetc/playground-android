package su.levenetc.androidplayground.raytracer.lights;

import su.levenetc.androidplayground.raytracer.Ray;

public class PointLight extends Light {

    public PointLight(double x, double y, int raysCount) {
        super(x, y, raysCount);
        initRays();
    }

    private void initRays() {

        double radius = 500;
        double angle = 0;
        double angleStep = 360d / raysCount;

        for (int i = 0; i < raysCount; i++) {

            double dx = radius * Math.cos(angle) + x;
            double dy = radius * Math.sin(angle) + y;

            rays.add(new Ray(x, y, dx, dy));

            angle += angleStep;
        }
    }
}