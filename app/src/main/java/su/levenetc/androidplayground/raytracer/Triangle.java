package su.levenetc.androidplayground.raytracer;

public class Triangle extends Shape {
    public Triangle() {
        super(3);
    }

    public void init(
            double x1, double y1,
            double x2, double y2,
            double x3, double y3
    ) {
        lines.get(0).set(x1, y1, x2, y2);
        lines.get(1).set(x2, y2, x3, y3);
        lines.get(2).set(x3, y3, x1, y1);
        initNormals();
    }
}