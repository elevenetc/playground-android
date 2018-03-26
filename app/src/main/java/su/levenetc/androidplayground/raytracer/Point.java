package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Point {

    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
