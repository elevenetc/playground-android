package su.levenetc.androidplayground.raytracer.shapes;

import android.graphics.Color;

import su.levenetc.androidplayground.raytracer.TransparentEdge;

public class BasicPrism extends Shape {

    public BasicPrism(double x, double y, double size) {

        super();

        for (int i = 0; i < 3; i++) {
            TransparentEdge edge = new TransparentEdge();
            edge.leftColor = Color.WHITE;
            edge.rightColor = Color.RED;
            edges.add(edge);
        }

        double side = 100 * size;
        double height = 50 * size;
        edges.get(0).set(x, y, x + side, y + side + height);
        edges.get(1).set(x + side, y + side + height, x - side, y + side + height);
        edges.get(2).set(x - side, y + side + height, x, y);

    }
}
