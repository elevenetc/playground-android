package su.levenetc.androidplayground.raytracer.edges;

import android.graphics.Color;

public class EdgeFactories {

    public static EdgeFactory transparent() {
        return transparent(Color.WHITE);
    }

    public static EdgeFactory transparent(int color) {
        return () -> {
            Edge edge = new Edge();
            edge.setBehaviour(Edge.Side.Behaviour.TRANSPARENT);
            edge.setLeftNormal();
            edge.setRightNormal();
            edge.leftSide().setColor(color);
            edge.rightSide().setColor(color);
            edge.leftSide().setType(Edge.Side.Type.OUT);
            edge.rightSide().setType(Edge.Side.Type.IN);
            return edge;
        };
    }

    public static EdgeFactory boundingBox() {
        return () -> {
            Edge edge = new Edge();
            edge.setBehaviour(Edge.Side.Behaviour.OPAQUE);
            edge.setRightNormal();
            return edge;
        };
    }

    public static EdgeFactory opaqueObjects() {
        return () -> {
            Edge edge = new Edge();
            edge.setBehaviour(Edge.Side.Behaviour.OPAQUE);
            edge.setLeftNormal();
            return edge;
        };
    }

    public interface EdgeFactory {
        Edge create();
    }
}