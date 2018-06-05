package su.levenetc.androidplayground.raytracer.math;

import su.levenetc.androidplayground.raytracer.edges.Edge;
import su.levenetc.androidplayground.raytracer.geometry.Point;

public class Intersection {

    public Point point;
    public Edge edge;
    public Edge.Side side;
    public int outColor;
    public boolean hasOutColor;

    public boolean exists() {
        return point != null;
    }
}
