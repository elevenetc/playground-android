package su.levenetc.androidplayground.raytracer.lights;

public class SingleRayLight extends DirectedLight {
    public SingleRayLight(double x, double y, double dirX, double dirY, int color) {
        super(x, y, dirX, dirY, color, 1);
        biased = false;
        initRays();
    }
}
