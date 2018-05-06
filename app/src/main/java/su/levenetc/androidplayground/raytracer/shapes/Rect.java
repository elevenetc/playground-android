package su.levenetc.androidplayground.raytracer.shapes;

import su.levenetc.androidplayground.raytracer.EdgeFactories;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Rect extends Shape {

    public static Rect byLoc(double x, double y, double width, double height){
        return new Rect(y, x, x + width, y + height);
    }

    public Rect(double top, double left, double right, double bottom) {
        super(4, EdgeFactories.basic());

        edges.get(0).set(left, top, right, top);//top
        edges.get(1).set(right, top, right, bottom);//right
        edges.get(2).set(right, bottom, left, bottom);//bottom
        edges.get(3).set(left, bottom, left, top);//left

        edges.get(0).name = "top";
        edges.get(1).name = "right";
        edges.get(2).name = "bottom";
        edges.get(3).name = "left";
    }
}