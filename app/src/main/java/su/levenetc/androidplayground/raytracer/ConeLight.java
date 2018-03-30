package su.levenetc.androidplayground.raytracer;

public class ConeLight extends Light {

    private final double dirX;
    private final double dirY;

    public ConeLight(double x, double y, double dirX, double dirY) {
        super(x, y);
        this.dirX = dirX;
        this.dirY = dirY;
        init();
    }

    private void init() {

        rays.add(new Ray(x, y, dirX, dirY));

        int count = 50;
        double angleDiff = 45d / count;

        for (int i = 0; i < count; i++) {
            this.rays.add(new Ray(x, y, dirX, dirY));
        }

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
}
