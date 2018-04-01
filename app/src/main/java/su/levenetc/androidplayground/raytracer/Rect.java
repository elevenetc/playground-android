package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Rect extends Shape {

    public static Rect byLoc(double x, double y, double width, double height){
        return new Rect(y, x, x + width, y + height);
    }

    public Rect(double top, double left, double right, double bottom) {
        super(4);

        lines.get(0).set(left, top, right, top);//top
        lines.get(1).set(right, top, right, bottom);//right
        lines.get(2).set(right, bottom, left, bottom);//bottom
        lines.get(3).set(left, bottom, left, top);//left
    }
}