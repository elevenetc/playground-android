package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Rect extends Shape {

    public Rect(double top, double left, double right, double bottom) {
        super(4);

//        int padding = 60;
//        top += padding;
//        bottom -= padding;
//        left += padding;
//        right -= padding;

        lines.get(0).set(left, top, right, top);//top
        lines.get(1).set(right, top, right, bottom);//right
        lines.get(2).set(right, bottom, left, bottom);//bottom
        lines.get(3).set(left, bottom, left, top);//left

        //lines.get(1).initRightNormal();
        //initRightNormals();
    }
}