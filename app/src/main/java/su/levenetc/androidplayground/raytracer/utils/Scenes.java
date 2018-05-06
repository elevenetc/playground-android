package su.levenetc.androidplayground.raytracer.utils;

import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.SceneBuilder;
import su.levenetc.androidplayground.raytracer.shapes.Path;

public class Scenes {
    public static Scene randomSquares(int width, int height) {
        SceneBuilder sceneBuilder = new SceneBuilder(width, height);
        return sceneBuilder
                .setPadding(60)
                .addRect(700, 300, 150, 150)
                .addRect(300, 470, 150, 150)
                .addRect(300, 670, 250, 20)
                .addRect(400, 770, 50, 50)
                .addRect(400, 850, 50, 50)
                .addRect(400, 930, 50, 50)
                .addRect(600, 1200, 550, 20)
                .build();
    }

    public static Scene curvePath(int width, int height, double initX, double initY) {

        return new SceneBuilder(width, height)
                .add(new Path.Builder()
                        .add(initX, initY)
                        .append(100, 100)
                        .append(100, 0)
                        .append(100, -50)
                        .append(100, 150)
                        .append(0, 150)
                        .append(-100, 0)
                        .append(-100, -100)
                        .append(-100, 0)
                        .append(0, -50)
                        .append(-50, 0)
                        .append(0, 100)
                        .append(100, 0)
                        .append(100, 100)
                        .append(200, 0)
                        .append(0, -300)
                        .append(-100, -100)
                        .append(-100, 0)
                        .append(-100, 50)
                        .add(initX, initY)
                        .initRightNormals()
                        .build())
                .build();
    }

    public static Scene justVertical(int width, int height) {
        return new SceneBuilder(width, height)
                .addEdge(width / 2, 200,
                        width / 2, height - 200,
                        false)
                .build();
    }

    public static Scene justVerticalTransparent(int width, int height) {
        return new SceneBuilder(width, height)
                .addTransparentEdge(width / 2, 350,
                        width / 2, height - 350,
                        "middle-trans")
                .build();
    }

    public static Scene basicPrism(int width, int height) {
        return new SceneBuilder(width, height)
                .addBasicPrism(width / 2, height / 2, 3)
                .build();
    }

    public static Scene basicLens(int width, int height) {

        double x = width / 2;
        double y = height / 2;
        double size = 10;

        return new SceneBuilder(width, height)
                .addBasicLens(x, y, size)
                .build();
    }
}
