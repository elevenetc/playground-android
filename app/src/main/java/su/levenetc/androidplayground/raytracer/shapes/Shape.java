package su.levenetc.androidplayground.raytracer.shapes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.Edge;
import su.levenetc.androidplayground.raytracer.EdgeFactories;

public class Shape {

    public List<Edge> edges;

    public Shape() {
        edges = new LinkedList<>();
    }

    public Shape(int segments, EdgeFactories.EdgeFactory factory) {
        edges = new ArrayList<>(segments);
        for (int i = 0; i < segments; i++) {
            edges.add(factory.create());
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