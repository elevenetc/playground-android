package su.levenetc.androidplayground.raytracer.shapes;

import java.util.ArrayList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.Edge;

public class Shape {

    public List<Edge> edges;

    public Shape() {
        edges = new ArrayList<>();
    }

    public Shape(int segments) {
        edges = new ArrayList<>(segments);
        for (int i = 0; i < segments; i++) {
            edges.add(new Edge());
        }
    }

    public void initRightNormals() {
        for (Edge raySegment : edges) raySegment.setRightNormal();
    }

    public void initLeftNormals() {
        for (Edge raySegment : edges) raySegment.setLeftNormal();
    }

    public void translate(double x, double y) {
        for (Edge raySegment : edges) raySegment.translate(x, y);
    }

    public List<Edge> lines() {
        return edges;
    }
}