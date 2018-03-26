package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Vector {

    double x1;
    double y1;

    double x2;
    double y2;

    public Vector(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Vector(Vector start, double x2, double y2) {
        this.x1 = start.x1;
        this.y1 = start.y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Vector(Point start, Point end) {
        this.x1 = start.x;
        this.y1 = start.y;
        this.x2 = end.x;
        this.y2 = end.y;
    }


    public Vector() {

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
