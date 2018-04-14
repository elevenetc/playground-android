package su.levenetc.androidplayground.raytracer;

import su.levenetc.androidplayground.raytracer.geometry.Segment;

public class DoubleSidedEdge extends Edge {

    public Segment leftNormal;
    public Segment rightNormal;

    @Override
    public void set(double x1, double y1, double x2, double y2) {
        super.set(x1, y1, x2, y2);
        leftNormal = getLeftNormal();
        rightNormal = getRightNormal();
    }

    @Override
    public boolean hasNormal() {
        return leftNormal != null && rightNormal != null;
    }
}