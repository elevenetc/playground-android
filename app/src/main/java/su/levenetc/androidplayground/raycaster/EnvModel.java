package su.levenetc.androidplayground.raycaster;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class EnvModel {

    double left;
    double top;
    double bottom;
    double right;

    Vector topBound = new Vector();
    Vector bottomBound = new Vector();
    Vector leftBound = new Vector();
    Vector rightBound = new Vector();

    List<Vector> allBounds = new LinkedList<>();

    public EnvModel() {
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
    }
}
