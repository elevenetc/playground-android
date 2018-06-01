package su.levenetc.androidplayground.raytracer.shapes;

import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.edges.Edge;
import su.levenetc.androidplayground.raytracer.edges.EdgeFactories;

public class Path extends Shape {

    public Path(int segments, EdgeFactories.EdgeFactory factory) {
        super(segments, factory);
    }

    public static class Builder {

        public Builder add(double x, double y) {
            coordinates.add(new double[]{x, y});
            return this;
        }
        private EdgeFactories.EdgeFactory factory;

        public Builder setFactory(EdgeFactories.EdgeFactory factory) {
            this.factory = factory;
            return this;
        }

        public Builder append(double dx, double dy) {
            double[] last = coordinates.get(coordinates.size() - 1);
            return add(last[0] + dx, last[1] + dy);
        }

        public Path build() {
            int segments = coordinates.size() - 1;
            Path path = new Path(segments, factory);

            for (int i = 0; i <= segments - 1; i++) {
                Edge edge = path.edges.get(i);
                double[] start = coordinates.get(i);
                double[] end = coordinates.get(i + 1);
                edge.set(start[0], start[1], end[0], end[1]);
            }

            return path;
        }

        List<double[]> coordinates = new LinkedList<>();
    }
}
