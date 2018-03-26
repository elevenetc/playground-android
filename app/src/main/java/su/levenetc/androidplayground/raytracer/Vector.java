package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Vector {

    double x1;
    double y1;

    double x2;
    double y2;

    Vector normal;

    public Vector(double x1, double y1, double x2, double y2) {
        set(x1, y1, x2, y2);
    }

    public Vector(Vector start, double x2, double y2) {
        set(start.x1, start.y1, x2, y2);
    }

    public Vector(Point start, Point end) {
        set(start.x, start.y, end.x, end.y);
    }


    public Vector() {

    }

    public void initLeftNormal() {

    }

    public void initRightNormal() {

    }

    public void setNormal(double x1, double y1, double x2, double y2) {
        normal = new Vector(x1, y1, x2, y2);
    }

    public void set(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getNorm() {
        return Math.sqrt(x1 * x2 + y1 * y2);
    }

    public double magnitude() {
        double xSide = x2 - x1;
        double ySide = y2 - y1;
        return Math.sqrt(xSide * xSide + ySide * ySide);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }
}
