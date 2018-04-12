package su.levenetc.androidplayground.raytracer;

public class ConeLight extends Light {

    public double dirX;
    public double dirY;
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

    public void updateDirection(double x, double y) {




        double ratio = 100;
        double dx = x - this.x;
        double dy = y - this.y;
        double endX = this.x + dx * ratio;
        double endY = this.y + dy * ratio;

        this.dirX = x;
        this.dirY = y;

        for (Ray ray : rays) {
            ray.initVector.x2 = endX;
            ray.initVector.y2 = endY;
        }

        rotateInitVectors();
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

        //define end locations towards direction
        //should be long to test intersections later
        double ratio = 100;
        double dx = dirX - x;
        double dy = dirY - y;
        double endX = x + dx * ratio;
        double endY = y + dy * ratio;

        for (int i = 0; i < raysCount; i++) {
            rays.add(new Ray(x, y, endX, endY));
        }
    }

    private void rotateInitVectors() {
        double coneAngle = 45d;
        double stepAngle = coneAngle / raysCount;

        double angle = 0;

        for (int i = 0; i < rays.size(); i++) {
            Ray ray = rays.get(i);
            RayMath.rotateSegment(this.rays.get(i).initVector, angle + getDirectionBias() - coneAngle / 2);
            angle += stepAngle;
        }
    }


    private double getDirectionBias() {
        return Math.random() * 0.5;
    }

}
