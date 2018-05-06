package su.levenetc.androidplayground.raytracer;

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
        rect.initLeftNormals();
        scene.add(rect);
        return this;
    }

    public SceneBuilder addEdge(double x1, double y1,
                                double x2, double y2,
                                boolean leftNormal
    ) {
        Line line = new Line(x1, y1, x2, y2);

        if (leftNormal) line.initLeftNormals();
        else line.initRightNormals();

        scene.add(line);
        return this;
    }


    public SceneBuilder addTransparentEdge(double x1, double y1,
                                           double x2, double y2,
                                           String name
    ) {
        Edge edge = new TransparentEdge();
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
        DoubleSidedEdge edge = new DoubleSidedEdge();
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

    public SceneBuilder addBasicLens(double x, double y, double size) {
        Path prism = new Path.Builder()
                .setFactory(EdgeFactories.transparent())
                .add(x, y)
                //right side
                .append(5 * size, 10 * size)
                .append(3 * size, 10 * size)
                .append(2 * size, 10 * size)
                .append(1 * size, 10 * size)
                .append(0, 10 * size)
                .append(-1 * size, 10 * size)
                .append(-2 * size, 10 * size)
                .append(-3 * size, 10 * size)
                .append(-5 * size, 10 * size)
                //left side
                .append(-5 * size, -10 * size)
                .append(-3 * size, -10 * size)
                .append(-2 * size, -10 * size)
                .append(-1 * size, -10 * size)
                .append(0, -10 * size)
                .append(1 * size, -10 * size)
                .append(2 * size, -10 * size)
                .append(3 * size, -10 * size)
                .append(5 * size, -10 * size)
                .build();
        scene.add(prism);
        return this;
    }

    public Scene build() {
        Rect boundRect = new Rect(padding, padding, screenWidth - padding, screenHeight - padding);

        boundRect.initRightNormals();
        scene.add(boundRect);

        return scene;
    }
}
