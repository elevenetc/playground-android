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

        double endX = x + 2000;
        double endY = y + 2000;

        rays.add(new Ray(x, y, dirX, dirY));

        for (int i = 0; i < 10; i++) {
            rays.add(new Ray(x, y, dirX, dirY));
        }

        double angle = RayMath.angleBetween(x, y, dirX, dirY, x, y, x + 100, y);
        double cAngle = angle;

        for (int i = 0; i <= 4; i++) {
            cAngle += 5;
            RayMath.rotateLine(rays.get(i).initVector, cAngle);
        }

        cAngle = angle;

        for (int i = 5; i <= 9; i++) {
            cAngle -= 5;
            RayMath.rotateLine(rays.get(i).initVector, cAngle);
        }
    }
}
