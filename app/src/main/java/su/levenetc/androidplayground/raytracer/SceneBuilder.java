package su.levenetc.androidplayground.raytracer;

import android.graphics.Color;

import su.levenetc.androidplayground.raytracer.edges.Edge;
import su.levenetc.androidplayground.raytracer.edges.EdgeFactories;
import su.levenetc.androidplayground.raytracer.shapes.BasicPrism;
import su.levenetc.androidplayground.raytracer.shapes.Line;
import su.levenetc.androidplayground.raytracer.shapes.Path;
import su.levenetc.androidplayground.raytracer.shapes.Rect;

public class SceneBuilder {

    private final int screenWidth;
    private final int screenHeight;
    private int padding;
    private Scene scene = new Scene();

    public SceneBuilder(int screenWidth, int screenHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public SceneBuilder setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public SceneBuilder addBasicPrism(double x, double y, double size) {
        BasicPrism prism = new BasicPrism(x, y, size);
        scene.add(prism);
        return this;
    }

    public SceneBuilder addRect(double x, double y, double width, double height) {
        Rect rect = new Rect(x, y, width, height);
        scene.add(rect);
        return this;
    }

    public SceneBuilder addEdge(double x1, double y1,
                                double x2, double y2
    ) {
        Line line = new Line(x1, y1, x2, y2);
        scene.add(line);
        return this;
    }

    public SceneBuilder addTransparentEdge(double x1, double y1,
                                           double x2, double y2,
                                           String name
    ) {
        Edge edge = EdgeFactories.transparent().create();
        edge.set(x1, y1, x2, y2);
        edge.name = name;

        Line line = new Line(edge);
        scene.add(line);
        return this;
    }

    public SceneBuilder addTransparentEdge(double x1, double y1,
                                           double x2, double y2
    ) {
        return addTransparentEdge(x1, y1, x2, y2, null);
    }

    public SceneBuilder addDoubleSidedEdge(double x1, double y1,
                                           double x2, double y2,
                                           String name
    ) {
        Edge edge = EdgeFactories.transparent().create();
        edge.set(x1, y1, x2, y2);
        edge.name = name;

        Line line = new Line(edge);
        scene.add(line);
        return this;
    }

    public SceneBuilder addDoubleSidedEdge(double x1, double y1,
                                           double x2, double y2
    ) {
        return addDoubleSidedEdge(x1, y1, x2, y2, null);
    }

    public SceneBuilder add(Path path) {
        scene.add(path);
        return this;
    }

    public SceneBuilder addBasicLens(double x, double y, double width, double height) {
        Path.Builder builder = null;
        double radius = height;
        double ratio = height / width;
        double stepRad = 1;
        double s = 0;

        while (s <= 360) {
            double rad = Math.toRadians(s);
            double xc = x + (radius * Math.sin(rad)) / ratio;
            double yc = y - radius * Math.cos(rad);

            if (builder == null) {
                builder = new Path.Builder().setFactory(EdgeFactories.transparent(Color.GREEN)).add(xc, yc);
            }

            builder.add(xc, yc);

            s += stepRad;
        }

        scene.add(builder.build());
        return this;
    }

    public Scene build() {
        Rect boundRect = new Rect(
                padding,
                padding,
                screenWidth - padding,
                screenHeight - padding,
                EdgeFactories.boundingBox());
        scene.add(boundRect);
        return scene;
    }
}
