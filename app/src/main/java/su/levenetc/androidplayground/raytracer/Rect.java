package su.levenetc.androidplayground.raytracer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Rect {

    double left;
    double top;
    double bottom;
    double right;

    Line topBound = new Line();
    Line bottomBound = new Line();
    Line leftBound = new Line();
    Line rightBound = new Line();

    List<Line> allBounds = new LinkedList<>();

    public Rect() {
        allBounds.add(topBound);
        allBounds.add(bottomBound);
        allBounds.add(leftBound);
        allBounds.add(rightBound);
    }

    public void set(double left, double top, double bottom, double right) {
        this.left = left;
        this.top = top;
        this.bottom = bottom;
        this.right = right;

        topBound.set(left, top, right, top);
        bottomBound.set(left, bottom, right, bottom);
        leftBound.set(left, bottom, left, top);
        rightBound.set(right, bottom, right, top);

        topBound.setNormal((right - left) / 2, top, (right - left) / 2, top + 100);
        bottomBound.setNormal((right - left) / 2, bottom, (right - left) / 2, bottom - 100);

        leftBound.setNormal(left, (bottom - top) / 2, left + 100, (bottom - top) / 2);
        rightBound.setNormal(right, (bottom - top) / 2, right - 100, (bottom - top) / 2);
    }
}
