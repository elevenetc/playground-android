package su.levenetc.androidplayground.raytracer;

import su.levenetc.androidplayground.raytracer.geometry.Point;
import su.levenetc.androidplayground.raytracer.geometry.Segment;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RaySegment extends Segment {

    public double start;
    public double end;

    public RaySegment(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }

    public RaySegment(RaySegment start, double x2, double y2) {
        super(start.x1, start.y1, x2, y2);
    }

    public RaySegment(Point start, Point end) {
        super(start.x, start.y, end.x, end.y);
    }

    public RaySegment() {

    }

    @Override
    public String toString() {
        return "RaySegment{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }

    public RaySegment copy() {
        return new RaySegment(x1, y1, x2, y2);
    }

}
