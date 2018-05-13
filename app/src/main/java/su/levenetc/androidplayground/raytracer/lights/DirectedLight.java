package su.levenetc.androidplayground.raytracer.lights;

import su.levenetc.androidplayground.raytracer.Ray;

abstract public class DirectedLight extends Light {

    double dirX;
    double dirY;
    boolean biased = true;

    public DirectedLight(double x, double y, double dirX, double dirY, int raysCount) {
        super(x, y, raysCount);
        this.dirX = dirX;
        this.dirY = dirY;
    }

    @Override
    public void updatePosition(double x, double y) {
        double dx = x - this.x;
        double dy = y - this.y;
        super.updatePosition(x, y);

        dirX += dx;
        dirY += dy;
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
    }

    protected void initRays() {

        //define end locations towards direction
        //should be long to test intersections later
        double ratio = 100;
        double dx = dirX - x;
        double dy = dirY - y;
        double endX = x + dx * ratio;
        double endY = y + dy * ratio;

        for (int i = 0; i < raysCount; i++) {
            this.rays.add(new Ray(x, y, endX, endY));
        }
    }
}
