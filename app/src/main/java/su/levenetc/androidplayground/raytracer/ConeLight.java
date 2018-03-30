package su.levenetc.androidplayground.raytracer;

public class ConeLight extends Light {

    private double dirX;
    private double dirY;
    int count = 100;

    public ConeLight(double x, double y, double dirX, double dirY) {
        super(x, y);
        this.dirX = dirX;
        this.dirY = dirY;
        init();
    }

    private void init() {
        initRays();
        rotateInitVectors();
    }

    private void rotateInitVectors() {
        double angleDiff = 45d / count;

        double angle = 0;

        for (int i = 0; i <= (count / 2) - 1; i++) {
            angle += angleDiff;
            RayMath.rotateLine(this.rays.get(i).initVector, angle);
        }

        angle = 0;

        for (int i = (count / 2); i <= (count - 1); i++) {
            angle -= angleDiff;
            RayMath.rotateLine(this.rays.get(i).initVector, angle);
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
        for (int i = 0; i < count; i++)
            this.rays.add(new Ray(x, y, dirX, dirY));
    }

}
