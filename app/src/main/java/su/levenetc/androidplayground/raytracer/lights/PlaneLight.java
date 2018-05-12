package su.levenetc.androidplayground.raytracer.lights;

import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.RayMath;

public class PlaneLight extends DirectedLight {

    private double currentAngle = 0;

    public PlaneLight(int rays, double x, double y, double dirX, double dirY) {
        super(x, y, dirX, dirY);
        initRays(rays, dirX, dirY);
        arrangeRays();
    }

    @Override
    public void updateDirection(double dx, double dy) {

        double newAngle = RayMath.angleBetween(x, y, x + 10, y, x, y, dx, dy);
        double currAngle = RayMath.angleBetween(x, y, x + 10, y, x, y, dirX, dirX);
        double diffAngle = currAngle - newAngle;
        dirX = dx;
        dirY = dy;
        for (int i = 0; i < rays.size(); i++) {

            RayMath.rotate(rays.get(i).initVector, x, y, diffAngle);
        }

    }

    private void arrangeRays() {
        double trans = 0;
        for (Ray ray : rays) {
            ray.initVector.translate(0, trans);
            trans += 4;
        }
    }

    private void rotateInitVectors() {

    }
}
