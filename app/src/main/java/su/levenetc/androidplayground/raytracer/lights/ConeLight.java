package su.levenetc.androidplayground.raytracer.lights;

import su.levenetc.androidplayground.raytracer.RayMath;

public class ConeLight extends DirectedLight {

    public ConeLight(double x, double y, double dirX, double dirY, int raysCount) {
        super(x, y, dirX, dirY, raysCount);
        initRays();
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
            RayMath.rotate(this.rays.get(i).initSegment, angle + (biased ? getDirectionBias() : 0) - coneAngle / 2);
            angle += stepAngle;
        }
    }

    private double getDirectionBias() {
        return Math.random() * 0.5;
    }

}
