package su.levenetc.androidplayground.raytracer;

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

    public SceneBuilder addRect(double x, double y, double width, double height) {
        Rect rect = new Rect(x, y, width, height);
        rect.initLeftNormals();
        scene.add(rect);
        return this;
    }

    public SceneBuilder add(Path path) {
        scene.add(path);
        return this;
    }

    public Scene build() {
        Rect boundRect = new Rect(padding, padding, screenWidth - padding, screenHeight - padding);
        boundRect.initRightNormals();
        scene.add(boundRect);

        return scene;
    }
}
