package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Line {

    double x1;
    double y1;

    double x2;
    double y2;

    Line normal;

    public Line(double x1, double y1, double x2, double y2) {
        set(x1, y1, x2, y2);
    }

    public Line(Line start, double x2, double y2) {
        set(start.x1, start.y1, x2, y2);
    }

    public Line(Point start, Point end) {
        set(start.x, start.y, end.x, end.y);
    }


    public Line() {

    }

    public void translate(double x, double y) {
        x1 += x;
        x2 += x;
        y1 += y;
        y2 += y;
    }

    public void initLeftNormal() {

        normal = new Line();

        if (isHorizontal()) {

        } else if (isVertical()) {

        } else {
            double s = -1 / slope();
            double length = length();

            normal.x1 = (x1 + x2) / 2d;
            normal.y1 = (y1 + y2) / 2d;

            double b = -s * normal.x1 + normal.y1;


            normal.x2 = normal.x2 + length;
            normal.y2 = (int) (s * (normal.x1 + length) + b);
        }
    }

    public void initRightNormal() {

    }

    public boolean isHorizontal() {
        //TODO: cache/optimize
        return y1 == y2;
    }

    public boolean isVertical() {
        //TODO: cache/optimize
        return x1 == x2;
    }

    public void setNormal(double x1, double y1, double x2, double y2) {
        normal = new Line(x1, y1, x2, y2);
    }

    public void set(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double length() {
        //TODO: cache/optimize
        double xSide = x2 - x1;
        double ySide = y2 - y1;
        return Math.sqrt(xSide * xSide + ySide * ySide);
    }

    public double slope() {
        //TODO: cache/optimize
        return (y2 - y1) / (x2 - x1);
    }

    @Override
    public String toString() {
        return "Line{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }
}
