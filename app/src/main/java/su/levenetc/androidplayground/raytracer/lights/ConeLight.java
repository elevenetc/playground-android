package su.levenetc.androidplayground.raytracer.lights;

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
}
