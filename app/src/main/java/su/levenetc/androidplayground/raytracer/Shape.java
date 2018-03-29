package su.levenetc.androidplayground.raytracer;

import java.util.ArrayList;
import java.util.List;

public class Shape {

    List<Line> lines;

    public Shape(int segments) {
        lines = new ArrayList<>(segments);
        for (int i = 0; i < segments; i++) {
            lines.add(new Line());
        }
    }

    protected void initNormals() {
        for (Line line : lines) {
            line.initLeftNormal();
        }
    }

    public void translate(double x, double y) {
        for (Line line : lines) line.translate(x, y);
    }
}
