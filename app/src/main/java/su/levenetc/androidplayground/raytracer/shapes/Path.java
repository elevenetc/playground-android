package su.levenetc.androidplayground.raytracer.shapes;

import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.Edge;

public class Path extends Shape {
    public Path(int segments) {
        super(segments);
    }

    public static class Builder {

        List<double[]> coords = new LinkedList<>();

        private boolean initLeftNormals;
        private boolean initRightNormals;

        public Builder add(double x, double y) {
            coords.add(new double[]{x, y});
            return this;
        }

        public Builder append(double dx, double dy) {
            double[] last = coords.get(coords.size() - 1);
            return add(last[0] + dx, last[1] + dy);
        }

        public Builder initRightNormals() {
            initRightNormals = true;
            initLeftNormals = false;
            return this;
        }

        public Builder initLeftNormals() {
            initLeftNormals = true;
            initRightNormals = false;
            return this;
        }

        public Path build() {
            int segments = coords.size() - 1;
            Path path = new Path(segments);

            for (int i = 0; i <= segments - 1; i++) {
                Edge edge = path.edges.get(i);
                double[] start = coords.get(i);
                double[] end = coords.get(i + 1);
                edge.set(start[0], start[1], end[0], end[1]);
            }

            if (initRightNormals) {
                path.initRightNormals();
            } else if (initLeftNormals) {
                path.initLeftNormals();
            }

            return path;
        }
    }
}
