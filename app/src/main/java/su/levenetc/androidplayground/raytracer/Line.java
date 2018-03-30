package su.levenetc.androidplayground.raytracer;

import static su.levenetc.androidplayground.raytracer.Line.Direction.E;
import static su.levenetc.androidplayground.raytracer.Line.Direction.N;
import static su.levenetc.androidplayground.raytracer.Line.Direction.NE;
import static su.levenetc.androidplayground.raytracer.Line.Direction.S;
import static su.levenetc.androidplayground.raytracer.Line.Direction.SE;
import static su.levenetc.androidplayground.raytracer.Line.Direction.SW;
import static su.levenetc.androidplayground.raytracer.Line.Direction.W;

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
        initNormal(50);
    }

    public void initRightNormal() {
        initNormal(-50);
    }

    private void initNormal(double lengthAndDirection) {
        normal = new Line();
        Direction direction = direction();

        normal.x1 = (x1 + x2) / 2d;
        normal.y1 = (y1 + y2) / 2d;

        if (direction == N) {
            normal.x2 = normal.x1 - lengthAndDirection;
            normal.y2 = normal.y1;
        } else if (direction == E) {
            normal.x2 = normal.x1;
            normal.y2 = normal.y1 - lengthAndDirection;
        } else if (direction == S) {
            normal.x2 = normal.x1 + lengthAndDirection;
            normal.y2 = normal.y1;
        } else if (direction == W) {
            normal.x2 = normal.x1;
            normal.y2 = normal.y1 + lengthAndDirection;
        } else {
            double s = -1 / slope();
            double length = lengthAndDirection;

            double b = -s * normal.x1 + normal.y1;

            double normalSide;

            if (direction == NE) {
                normalSide = normal.x1 - length;
            } else if (direction == SE) {
                normalSide = normal.x1 + length;
            } else if (direction == SW) {
                normalSide = normal.x1 + length;
            } else {
                normalSide = normal.x1 - length;
            }

            normal.x2 = normalSide;
            normal.y2 = (s * (normalSide) + b);
        }
    }

    public boolean isHorizontal() {
        //TODO: cache/optimize
        return y1 == y2;
    }

    public boolean isVertical() {
        //TODO: cache/optimize
        return x1 == x2;
    }

    public boolean hasNormal() {
        return normal != null;
    }

    public Line normal() {
        return normal;
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

    /**
     * @return direction in android Canvas coordinates:
     * N
     * NW(-1:-1) |  (1:-1)NE
     * |
     * W--------|-------E
     * |
     * SW(-1:1)  |  (1:1)SE
     * S
     */
    public Direction direction() {
        //TODO: cache/optimize
        if (y1 == y2) {

            if (x1 < x2) {
                return E;
            } else {
                return W;
            }

        } else if (x1 == x2) {

            if (y1 < y2) {
                return S;
            } else {
                return N;
            }

        } else if (x1 < x2 && y1 < y2) {
            return SE;
        } else if (x1 < x2 && y1 > y2) {
            return NE;
        } else if (x1 > x2 && y1 > y2) {
            return Direction.NW;
        } else {
            return SW;
        }
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

    public enum Direction {
        N, E, S, W,
        NE, SE, SW, NW
    }
}
