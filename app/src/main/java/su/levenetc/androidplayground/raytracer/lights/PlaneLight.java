package su.levenetc.androidplayground.raytracer.lights;

import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.RayMath;

public class PlaneLight extends DirectedLight {

    private static final double rayStep = 2;

    public PlaneLight(double x, double y, double dirX, double dirY, int color, int raysCount) {
        super(x, y, dirX, dirY, color, raysCount);
        initRays();
        arrangeRays();
    }

    @Override
    public void updateDirection(double dx, double dy) {

        double newAngle = RayMath.angleBetween(x, y, x + 10, y, x, y, dx, dy);
        double currAngle = RayMath.angleBetween(x, y, x + 10, y, x, y, dirX, dirY);
        double diffAngle = currAngle - newAngle;
        dirX = dx;
        dirY = dy;
        for (int i = 0; i < rays.size(); i++) {
            RayMath.rotate(rays.get(i).initSegment, x, y, diffAngle);
        }
    }

    private void arrangeRays() {

        double trans = (raysCount / 2 * rayStep) * -1;
        for (Ray ray : rays) {
            ray.initSegment.translate(0, trans);
            trans += rayStep;
        }
    }
}