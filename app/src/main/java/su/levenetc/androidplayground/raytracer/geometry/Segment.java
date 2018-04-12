package su.levenetc.androidplayground.raytracer.geometry;

import su.levenetc.androidplayground.raytracer.RaySegment;

import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.E;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.N;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.NE;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.S;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.SE;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.SW;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.W;

public class Segment {
    public double x1;
    public double y1;

    public double x2;
    public double y2;

    public Segment(double x1, double y1, double x2, double y2) {
        set(x1, y1, x2, y2);
    }

    public Segment(RaySegment start, double x2, double y2) {
        set(start.x1, start.y1, x2, y2);
    }

    public Segment(Point start, Point end) {
        set(start.x, start.y, end.x, end.y);
    }

    public Segment() {

    }

    public void translate(double dx, double dy) {
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }

    public void translateEnd(double dx2, double dy2) {
        x2 += dx2;
        y2 += dy2;
    }

    public void translateTo(Point intersection) {
        double xDiff = intersection.x - x1;
        double yDiff = intersection.y - y1;
        translate(xDiff, yDiff);
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

    public boolean isHorizontal() {
        //TODO: cache/optimize
        return y1 == y2;
    }

    public boolean isVertical() {
        //TODO: cache/optimize
        return x1 == x2;
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

    public enum Direction {
        N, E, S, W,
        NE, SE, SW, NW
    }
}
