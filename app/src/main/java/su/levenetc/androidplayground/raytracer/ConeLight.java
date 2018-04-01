package su.levenetc.androidplayground.raytracer;

public class ConeLight extends Light {

    private double dirX;
    private double dirY;
    int raysCount;

    public ConeLight(int rays, double x, double y, double dirX, double dirY) {
        super(x, y);
        this.dirX = dirX;
        this.dirY = dirY;
        this.raysCount = rays;
        init();
    }

    private void init() {
        initRays();
        rotateInitVectors();
    }

    private void rotateInitVectors() {
        double angleDiff = 45d / raysCount;

        double angle = 0;

        for (int i = 0; i <= (raysCount / 2) - 1; i++) {
            angle += angleDiff;
            RayMath.rotateLine(this.rays.get(i).initVector, angle + getDirectionBias());
        }

        angle = 0;

        for (int i = (raysCount / 2); i <= (raysCount - 1); i++) {
            angle -= angleDiff;
            RayMath.rotateLine(this.rays.get(i).initVector, angle + getDirectionBias());
        }
    }

    public void updatePosition(double x, double y) {

        double dx = x - this.x;
        double dy = y - this.y;

        this.x = x;
        this.y = y;

        dirX += dx;
        dirY += dy;

        for (Ray ray : rays) {
            ray.initVector.translate(dx, dy);
        }
    }

    private void initRays() {
        rays.add(new Ray(x, y, dirX, dirY));
        for (int i = 0; i < raysCount; i++)
            this.rays.add(new Ray(x, y, dirX, dirY));
    }

    private double getDirectionBias() {
        return Math.random() * 0.5;
    }

}
