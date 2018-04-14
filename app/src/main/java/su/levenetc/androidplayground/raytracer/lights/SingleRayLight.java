package su.levenetc.androidplayground.raytracer.lights;

public class SingleRayLight extends DirectedLight {
    public SingleRayLight(double x, double y, double dirX, double dirY) {
        super(x, y, dirX, dirY);
        biased = false;
        initRays(1, dirX, dirY);
    }
}
