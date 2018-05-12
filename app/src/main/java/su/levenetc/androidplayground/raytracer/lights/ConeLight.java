package su.levenetc.androidplayground.raytracer.lights;

import su.levenetc.androidplayground.raytracer.RayMath;

public class ConeLight extends DirectedLight {

    public ConeLight(int rays, double x, double y, double dirX, double dirY) {
        super(x, y, dirX, dirY);
        initRays(rays, dirX, dirY);
        rotateInitVectors(true);
    }

    @Override
    public void updateDirection(double x, double y) {
        super.updateDirection(x, y);
        rotateInitVectors(true);
    }

    private void rotateInitVectors(boolean biased) {
        double coneAngle = 25d;
        double stepAngle = coneAngle / rays.size();

        double angle = 0;

        for (int i = 0; i < rays.size(); i++) {
            RayMath.rotate(this.rays.get(i).initVector, angle + (biased ? getDirectionBias() : 0) - coneAngle / 2);
            angle += stepAngle;
        }
    }

    private double getDirectionBias() {
        return Math.random() * 0.5;
    }

}
