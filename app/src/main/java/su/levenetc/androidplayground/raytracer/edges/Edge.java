package su.levenetc.androidplayground.raytracer.edges;

import su.levenetc.androidplayground.raytracer.Normal;
import su.levenetc.androidplayground.raytracer.geometry.Segment;

import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.E;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.N;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.NE;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.S;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.SE;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.SW;
import static su.levenetc.androidplayground.raytracer.geometry.Segment.Direction.W;

public class Edge extends Segment {

    private Side left = new Side();
    private Side right = new Side();

    @Override
    public void set(double x1, double y1, double x2, double y2) {
        super.set(x1, y1, x2, y2);
        //Recalculate normals
        if (left.hasNormal()) left.normal = getLeftNormal();
        if (right.hasNormal()) right.normal = getRightNormal();
    }

    public Side leftSide() {
        return left;
    }

    public Side rightSide() {
        return right;
    }

    public void setLeftNormal() {
        left.normal = getLeftNormal();
    }

    public void setType(Side.Type type) {
        left.type = type;
        right.type = type;
    }

    public void setBehaviour(Side.Behaviour behaviour) {
        left.behaviour = behaviour;
        right.behaviour = behaviour;
    }

    public void setRightNormal() {
        right.normal = getRightNormal();
    }

    public Normal getRightNormal() {
        return getNormal(-50);
    }

    public Normal getLeftNormal() {
        return getNormal(50);
    }

    private Normal getNormal(double lengthAndDirection) {

        Normal normal = new Normal();
        Direction direction = direction();

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

    public static class Side {

        public boolean isInside() {
            return type == Type.IN;
        }

        public boolean isOutise() {
            return type == Type.OUT;
        }

        public boolean isTransparent() {
            return behaviour == Behaviour.TRANSPARENT;
        }

        public Normal normal() {
            return normal;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public boolean hasNormal() {
            return normal != null;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int color() {
            return color;
        }

        Normal normal;
        int color;
        Type type;
        Behaviour behaviour;

        enum Type {
            IN, OUT
        }

        enum Behaviour {
            TRANSPARENT, OPAQUE
        }
    }
}