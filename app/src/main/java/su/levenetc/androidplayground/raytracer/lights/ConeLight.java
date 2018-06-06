package su.levenetc.androidplayground.raytracer.lights;

import su.levenetc.androidplayground.raytracer.math.RayMath;

public class ConeLight extends DirectedLight {

    private RayMath math;

    public ConeLight(double x, double y, double dirX, double dirY, int color, int raysCount, RayMath math) {
        super(x, y, dirX, dirY, color, raysCount);
        this.math = math;
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
            math.rotate(this.rays.get(i).initSegment, angle + (biased ? getDirectionBias() : 0) - coneAngle / 2);
            angle += stepAngle;
        }
    }

    private double getDirectionBias() {
        return Math.random() * 0.5;
    }

}
