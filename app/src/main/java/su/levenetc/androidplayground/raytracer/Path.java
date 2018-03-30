package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

public class Path extends Shape {
    public Path(int segments) {
        super(segments);
    }

    static class Builder {

        List<double[]> coords = new LinkedList<>();

        public Builder add(double x, double y) {
            coords.add(new double[]{x, y});
            return this;
        }

        public Builder append(double dx, double dy) {
            double[] last = coords.get(coords.size() - 1);
            return add(last[0] + dx, last[1] + dy);
        }

        public Path build() {
            int segments = coords.size() - 1;
            Path path = new Path(segments);

            for (int i = 0; i <= segments - 1; i++) {
                Line line = path.lines.get(i);
                double[] start = coords.get(i);
                double[] end = coords.get(i + 1);
                line.set(start[0], start[1], end[0], end[1]);
            }
            return path;
        }
    }
}
