package su.levenetc.androidplayground.raytracer.lights;

public class SingleRayLight extends DirectedLight {
    public SingleRayLight(double x, double y, double dirX, double dirY) {
        super(x, y, dirX, dirY, 1);
        biased = false;
        initRays();
    }
}
