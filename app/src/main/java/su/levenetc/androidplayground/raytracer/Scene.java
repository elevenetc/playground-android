package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    List<Shape> objects = new LinkedList<>();

    public void add(Shape shape) {
        objects.add(shape);
    }

    public List<Shape> objects() {
        return objects;
    }
}