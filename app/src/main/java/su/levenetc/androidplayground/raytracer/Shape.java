package su.levenetc.androidplayground.raytracer;

import java.util.ArrayList;
import java.util.List;

public class Shape {

    List<Edge> edges;

    public Shape(int segments) {
        edges = new ArrayList<>(segments);
        for (int i = 0; i < segments; i++) {
            edges.add(new Edge());
        }
    }

    protected void initRightNormals() {
        for (Edge raySegment : edges) raySegment.initRightNormal();
    }

    protected void initLeftNormals() {
        for (Edge raySegment : edges) raySegment.initLeftNormal();
    }

    public void translate(double x, double y) {
        for (Edge raySegment : edges) raySegment.translate(x, y);
    }

    public List<Edge> lines() {
        return edges;
    }
}