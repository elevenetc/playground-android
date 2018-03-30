package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Rect extends Shape {

    public Rect(double top, double left, double right, double bottom) {
        super(4);
        lines.get(0).set(left, top, right, top);//top
        lines.get(1).set(right, bottom, right, top);//right
        lines.get(2).set(left, bottom, right, bottom);//bottom
        lines.get(3).set(left, bottom, left, top);//left
    }
}
