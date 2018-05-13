package su.levenetc.androidplayground.raytracer;

import su.levenetc.androidplayground.raytracer.geometry.Segment;

import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.E;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.N;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.NE;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.S;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.SE;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.SW;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.W;


public class Edge extends Segment {

    Segment normal;

    public Edge() {

    }

    public Edge(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }

    public void setLeftNormal() {
        normal = getLeftNormal();
    }

    public void setRightNormal() {
        normal = getRightNormal();
    }

    public Segment getRightNormal() {
        return getNormal(-50);
    }

    public Segment getLeftNormal() {
        return getNormal(50);
    }

    private Segment getNormal(double lengthAndDirection) {
        Segment normal = new Segment();
        RaySegment.Direction direction = direction();

        normal.x1 = (x1 + x2) / 2d;
        normal.y1 = (y1 + y2) / 2d;

        if (direction == N) {
            normal.x2 = normal.x1 - lengthAndDirection;
            normal.y2 = normal.y1;
        } else if (direction == E) {
            normal.x2 = normal.x1;
            normal.y2 = normal.y1 - lengthAndDirection;
        } else if (direction == S) {
            normal.x2 = normal.x1 + lengthAndDirection;
            normal.y2 = normal.y1;
        } else if (direction == W) {
            normal.x2 = normal.x1;
            normal.y2 = normal.y1 + lengthAndDirection;
        } else {
            double s = -1 / slope();
            double length = lengthAndDirection;

            double b = -s * normal.x1 + normal.y1;

            double normalSide;

            if (direction == NE) {
                normalSide = normal.x1 - length;
            } else if (direction == SE) {
                normalSide = normal.x1 + length;
            } else if (direction == SW) {
                normalSide = normal.x1 + length;
            } else {
                normalSide = normal.x1 - length;
            }

            normal.x2 = normalSide;
            normal.y2 = (s * (normalSide) + b);
        }

        return normal;
    }


    public boolean hasNormal() {
        return normal != null;
    }

    public Segment normal() {
        return normal;
    }

    public void setNormal(double x1, double y1, double x2, double y2) {
        normal = new RaySegment(x1, y1, x2, y2);
    }
}