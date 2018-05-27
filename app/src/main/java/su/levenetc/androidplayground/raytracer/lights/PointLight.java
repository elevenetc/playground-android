package su.levenetc.androidplayground.raytracer.lights;

import android.graphics.Color;

import su.levenetc.androidplayground.raytracer.Ray;

public class PointLight extends Light {

    public PointLight(double x, double y, double radius, int color, int raysCount) {
        super(x, y, color, raysCount);
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

            rays.add(new Ray(x, y, dx, dy, Color.RED));

            angle += angleStep;
        }
    }
}