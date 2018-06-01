package su.levenetc.androidplayground.raytracer.shapes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.edges.Edge;
import su.levenetc.androidplayground.raytracer.edges.EdgeFactories;

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

    public void translate(double x, double y) {
        for (Edge edge : edges) {
            edge.translate(x, y);
        }
    }

    public List<Edge> edges() {
        return edges;
    }
}