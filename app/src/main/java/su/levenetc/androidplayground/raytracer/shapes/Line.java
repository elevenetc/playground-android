package su.levenetc.androidplayground.raytracer.shapes;

import su.levenetc.androidplayground.raytracer.Edge;

public class Line extends Shape {
    public Line(double x1, double y1, double x2, double y2) {
        super(1);
        Edge e = edges.get(0);
        e.x1 = x1;
        e.y1 = y1;
        e.x2 = x2;
        e.y2 = y2;
        edges.add(e);
    }

    public Line(Edge edge) {
        super(1);
        edges.add(edge);
    }
}
